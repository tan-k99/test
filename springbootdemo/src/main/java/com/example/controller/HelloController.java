package com.example.controller;

import com.example.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述: demo
 * @作者: tankejia
 * @创建时间: 2018/2/23-10:50 .
 * @版本: 1.0 .
 */
@Slf4j
@RestController     //等同于同时加上了@Controller和@ResponseBody
public class HelloController {

    @RequestMapping("/hello")
    public String hello () {
        Person person = new Person();
        log.info("姓名",person.getName());
        return "Hello "+person.getName()+"!";
    }
}
