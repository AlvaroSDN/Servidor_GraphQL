package com.servidorGraphQL.code;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

public class Query implements GraphQLRootResolver {
    
    private final StudentRepository studentRepository;
    
    public Query(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> allStudents() {
        return studentRepository.getAllStudents();
    }

}
