package com.karasov.hibernatehw.repository;

import com.karasov.hibernatehw.entity.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> getPersonsByCity(String city);
}
