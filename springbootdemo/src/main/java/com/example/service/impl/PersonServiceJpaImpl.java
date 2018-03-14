package com.example.service.impl;

import com.example.exception.PersonException;
import com.example.model.Person;
import com.example.repository.PersonRep;
import com.example.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonServiceJpaImpl implements PersonService {

    @Autowired
    private PersonRep personRep;

    private static final Logger log = LoggerFactory.getLogger(PersonServiceJpaImpl.class);


    @Override
    public Integer addPerson(Person person) {
        log.info("PersonServiceImpl-->addPerson()-->person:{}",person.toString());
        // 返回新增数据的主键
        return personRep.saveAndFlush(person).getId();
    }

    @Override
    public Person updatePerson(Person person) throws PersonException {
        log.info("PersonServiceImpl-->updatePerson()-->person:{}",person.toString());
        Person person1 = personRep.findOne(person.getId());
        if (null == person1) {
            log.error("该id对应的对象不存在，id:{},修改失败",person.getId());
            throw new PersonException();
        }
        if (null != person.getName()) {
            person1.setName(person.getName());
        }
        if (null != person.getAge()) {
            person1.setAge(person.getAge());
        }
        personRep.save(person1);
        return person1;
    }

    @Override
    public Person deletePerson(int id) throws PersonException {
        log.info("PersonServiceImpl-->deletePerson()-->id:{}",id);
        Person personDelete = personRep.findOne(id);
        if (null == personDelete) {
            log.error("该id对应的对象不存在，id:{}，删除失败",id);
            throw new PersonException();
        }
        personRep.delete(personDelete);
        return personDelete;
    }

    @Override
    public Person queryPerson(int id) {
        log.info("PersonServiceImpl-->queryPerson()-->id:{}",id);
        return personRep.findOne(id);
    }

    @Override
    public List<Person> listPerson() {
        log.info("PersonServiceImpl-->listPerson()-->person:{}");
        return personRep.findAll();
    }
}
