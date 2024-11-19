package com.karasov.hibernatehw.service;

import com.karasov.hibernatehw.entity.Person;
import com.karasov.hibernatehw.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Override
    public List<Person> getPersonsByCity(String city) {
        return repository.getPersonsByCity(city);
    }
}
