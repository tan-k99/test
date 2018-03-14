package com.example.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

//    private String barCodeUrl;

    private Date birthday;

//    private List<Integer> accounts;

    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

//    public String getBarCodeUrl() {
//        return barCodeUrl;
//    }
//
//    public void setBarCodeUrl(String barCodeUrl) {
//        this.barCodeUrl = barCodeUrl;
//    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

//    public List<Integer> getAccounts() {
//        return accounts;
//    }
//
//    public void setAccounts(List<Integer> accounts) {
//        this.accounts = accounts;
//    }

    // 不重写toString方法的话打印的是地址
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

