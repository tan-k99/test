package com.example.controller;

import com.example.exception.PersonException;
import com.example.model.Person;
import com.example.service.PersonService;
import com.example.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/listPerson")
    public List<Person> listPerson() {
        return personService.listPerson();
    }

    @PostMapping("/addPerson")
    public Integer addPerson(@RequestBody Person person) {
         return personService.addPerson(person);
    }

    @PostMapping("/updatePerson")
    public Person updatePerson(@RequestBody Person person) {
        Person person1 = new Person();
        try {
            person1 = personService.updatePerson(person);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        return person1;
    }

    @GetMapping("/deletePerson")
    public Person deletePerson(int id) {
        Person person1 = new Person();
        try {
            person1 = personService.deletePerson(id);
        } catch (PersonException e) {
            e.printStackTrace();
        }
        return person1;
    }

    @GetMapping("/queryPerson")
    public Person queryOne(int id) {
        // 查询无结果
        return personService.queryPerson(id);
    }

    @GetMapping("/exportPdf")
    public void exportPdf(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Person person = PdfGenerator.generatePerson();
        PdfGenerator generator = new PdfGenerator();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        generator.generate(outputStream,person);

        resp.setContentType(MediaType.APPLICATION_PDF_VALUE);
        resp.setContentLength(outputStream.size());
        resp.setHeader("Content-disposition", "attachment;filename=" + person.getId() + ".pdf");
        ServletOutputStream sos = resp.getOutputStream();
        outputStream.writeTo(sos);
        sos.flush();
    }

}
