package com.servidorGraphQL.code;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

public class StudentRepository {
	
	private final MongoCollection<Document> students;

	public StudentRepository(MongoCollection<Document> students) {
		this.students = students;
	}

	public Student findById(String id) {
		Document doc = students.find(eq("_id", new ObjectId(id))).first();
		return student(doc);
	}

	public List<Student> getAllStudents() {
		List<Student> allStudents = new ArrayList<>();
		for (Document doc : students.find()) {
			allStudents.add(student(doc));
		}
		return allStudents;
	}

	public Student saveStudent(Student student) {
		Document doc = new Document();
		doc.append("name", student.getName());
		doc.append("email", student.getEmail());
		students.insertOne(doc);
		return student(doc);
	}

	private Student student(Document doc) {
		return new Student(
				doc.get("_id").toString(),
				doc.getString("name"),
				doc.getString("email"));
	}
	
}

