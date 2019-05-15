package com.servidorGraphQL.code;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

public class Mutation implements GraphQLRootResolver {
    
    private final StudentRepository studentRepository;

    public Mutation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    public Student createStudent(String name, String email) {
        Student newStudent = new Student(name, email);
        return studentRepository.saveStudent(newStudent);
    }
    
}
