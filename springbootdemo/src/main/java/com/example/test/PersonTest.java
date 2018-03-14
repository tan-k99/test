package com.example.test;

import com.example.model.Person;
import com.example.service.PersonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @描述: demo
 * @作者: tankejia
 * @创建时间: 2018/2/23-15:14 .
 * @版本: 1.0 .
 */
public class PersonTest {

    @Autowired
    private PersonService personService;

    @Test
    public void testAdd(){

    }

    @Test
    public void testQueryOne(){
        String classFile = "com.j4353d.". replaceAll("d", "/") + "MyClass.class";
        System.out.println(10 % 3 * 2);

    }

    @Test
    public void testQueryAll(){
        List<Person> personList = personService.listPerson();
        for (Person person : personList) {
            System.out.println(person.getName());
        }

    }

    @Test
    public void testDelete(){

    }

    @Test
    public void testUpdate(){

    }

}
