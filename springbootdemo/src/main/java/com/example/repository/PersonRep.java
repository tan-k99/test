package com.example.repository;

import com.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 使用Spring Data JPA
 * */
public interface PersonRep extends JpaRepository<Person, Integer> {
}
