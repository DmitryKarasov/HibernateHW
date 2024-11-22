package com.karasov.hibernatehw.service;

import com.karasov.hibernatehw.dto.PatchPersonDto;
import com.karasov.hibernatehw.dto.PersonDto;

import java.net.URI;
import java.util.List;

public interface PersonService {
    List<PersonDto> getPersonsByCity(String city);

    List<PersonDto> getPersonByAgeLowerThanAsc(int age);

    PersonDto getPersonByNameAndSurname(String name, String surname);

    void deletePerson(String name, String surname, int age);

    URI createPerson(PersonDto personDto);

    PersonDto getPerson(String name, String surname, int age);

    int patchPerson(String name, String surname, int age, PatchPersonDto patchPersonDto);
}
