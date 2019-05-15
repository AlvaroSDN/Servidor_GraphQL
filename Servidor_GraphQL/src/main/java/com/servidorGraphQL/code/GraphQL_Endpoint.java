package com.servidorGraphQL.code;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQL_Endpoint extends SimpleGraphQLServlet {
	
	private static final long serialVersionUID = 1L;
	private static final StudentRepository studentRepository;
    
	static {
		// "C:\Program Files\MongoDB\Server\4.0\bin\mongod.exe" --dbpath="c:\data\db"
		MongoDatabase mongo = new MongoClient().getDatabase("Database_GraphQL");
        studentRepository = new StudentRepository(mongo.getCollection("Students"));
    }
	
	public GraphQL_Endpoint() {
		super(buildSchema());
	}
	
	private static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
				.file("schema.graphqls")
				.resolvers(new Query(studentRepository), 
						new Mutation(studentRepository))
				.build()
				.makeExecutableSchema();
	}
	
	@Override
	protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
	    return errors.stream()
	            .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
	            .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
	            .collect(Collectors.toList());
	}
	
}
