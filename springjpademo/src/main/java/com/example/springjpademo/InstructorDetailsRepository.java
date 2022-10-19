package com.example.springjpademo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorDetailsRepository extends JpaRepository<Instructor, Long> {}
