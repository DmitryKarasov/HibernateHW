package com.karasov.hibernatehw.mapper;

import com.karasov.hibernatehw.dto.PersonDto;
import com.karasov.hibernatehw.entity.City;
import com.karasov.hibernatehw.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonDto personToDto(Person person) {
        return new PersonDto(
                person.getId().getName(),
                person.getId().getSurname(),
                person.getId().getAge(),
                person.getPhoneNumber(),
                person.getCityOfLiving().getName()
        );
    }

    public Person dtoToPerson(PersonDto personDto, City city) {
        return new Person(
                new Person.PersonId(
                        personDto.name(),
                        personDto.surname(),
                        personDto.age()
                ),
                personDto.phoneNumber(),
                city
        );
    }
}
