package com.example.service;


import com.example.exception.PersonException;
import com.example.model.Person;


import java.util.List;

public interface PersonService {

    Integer addPerson(Person person);
    Person updatePerson(Person person) throws PersonException;
    Person deletePerson(int id) throws PersonException;
    Person queryPerson(int id);
    List<Person> listPerson();

}
