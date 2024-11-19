package com.karasov.hibernatehw.service;

import com.karasov.hibernatehw.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> getPersonsByCity(String city);
}
