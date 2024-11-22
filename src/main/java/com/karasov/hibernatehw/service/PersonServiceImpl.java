package com.karasov.hibernatehw.service;

import com.karasov.hibernatehw.dto.PatchPersonDto;
import com.karasov.hibernatehw.dto.PersonDto;
import com.karasov.hibernatehw.entity.City;
import com.karasov.hibernatehw.entity.Person;
import com.karasov.hibernatehw.handler.exception.CityNotFoundException;
import com.karasov.hibernatehw.handler.exception.CreatingPersonAlreadyExistsException;
import com.karasov.hibernatehw.handler.exception.PersonNotFoundException;
import com.karasov.hibernatehw.mapper.PersonMapper;
import com.karasov.hibernatehw.repository.CityRepository;
import com.karasov.hibernatehw.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Реализация интерфейса {@link PersonService}.
 * Содержит бизнес-логику для управления сущностями {@link Person}.
 * Включает методы для получения, обновления, удаления и создания людей в системе.
 * Взаимодействует с {@link PersonRepository}, {@link CityRepository} и {@link PersonMapper}
 * для доступа к данным и их маппинга.
 */
@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final CityRepository cityRepository;
    private final PersonMapper personMapper;

    /**
     * Получает {@link PersonDto} по ключу: имени, фамилии и возрасту.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @param age     Возраст человека.
     * @return {@link PersonDto} с деталями человека.
     * @throws PersonNotFoundException Если человек с указанными параметрами не найден.
     */
    @Override
    public PersonDto getPerson(String name, String surname, int age) {
        return personRepository.findPersonById(new Person.PersonId(name, surname, age))
                .map(personMapper::personToDto)
                .orElseThrow(() -> new PersonNotFoundException(
                        String.format("Person with name '%s', surname '%s' and age %d not found.", name, surname, age)
                ));
    }

    /**
     * Обновляет информацию о человеке.
     * Выполняет частичное обновление (PATCH) данных.
     *
     * @param name           Имя человека.
     * @param surname        Фамилия человека.
     * @param age            Возраст человека.
     * @param patchPersonDto Объект с новыми данными для обновления.
     * @return Количество обновленных записей.
     * @throws CityNotFoundException Если город не найден.
     */
    @Transactional
    @Override
    public int patchPerson(String name, String surname, int age, PatchPersonDto patchPersonDto) {

        City city = cityRepository.findCityByName(patchPersonDto.cityName()).orElseThrow(() -> new CityNotFoundException(
                String.format("City '%s' not found.", patchPersonDto.cityName())
        ));

        Person.PersonId personId = new Person.PersonId(name, surname, age);
        return personRepository.updatePersonById(personId, patchPersonDto.phoneNumber(), city.getId());
    }

    /**
     * Получает список {@link PersonDto} всех людей, проживающих в указанном городе.
     *
     * @param city Название города.
     * @return Список {@link PersonDto} людей, проживающих в данном городе.
     */
    @Override
    public List<PersonDto> getPersonsByCity(String city) {
        return personRepository.getPersonsByCity(city).stream()
                .map(personMapper::personToDto)
                .toList();
    }

    /**
     * Получает список {@link PersonDto} людей, чей возраст меньше указанного, отсортированных по возрастанию.
     *
     * @param age Возраст, по которому будет фильтроваться список.
     * @return Список {@link PersonDto} людей, чей возраст меньше указанного.
     */
    @Override
    public List<PersonDto> getPersonByAgeLowerThanAsc(int age) {
        return personRepository.getPersonByAgeLowerThanAsc(age).stream()
                .map(personMapper::personToDto)
                .toList();
    }

    /**
     * Получает {@link PersonDto} по имени и фамилии.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @return {@link PersonDto} с деталями человека.
     * @throws PersonNotFoundException Если человек с указанными параметрами не найден.
     */
    @Override
    public PersonDto getPersonByNameAndSurname(String name, String surname) {
        return personRepository.getPersonByNameAndSurname(name, surname)
                .map(personMapper::personToDto)
                .orElseThrow(() -> new PersonNotFoundException(
                        String.format("Person with name '%s' and surname '%s' not found.", name, surname)
                ));
    }

    /**
     * Удаляет человека по ключу: имени, фамилии и возрасту.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @param age     Возраст человека.
     */
    @Transactional
    @Override
    public void deletePerson(String name, String surname, int age) {
        personRepository.deletePerson(name, surname, age);
    }

    /**
     * Создает нового человека.
     * Если человек с такими данными уже существует, выбрасывает исключение.
     *
     * @param personDto Объект с данными нового человека.
     * @return URI для доступа к созданному человеку.
     * @throws CreatingPersonAlreadyExistsException Если человек с такими данными уже существует.
     * @throws CityNotFoundException                Если город не найден.
     */
    @Transactional
    @Override
    public URI createPerson(PersonDto personDto) {
        if (personRepository.existsById(
                new Person.PersonId(
                        personDto.name(),
                        personDto.surname(),
                        personDto.age()
                ))) {
            throw new CreatingPersonAlreadyExistsException(
                    String.format("Person with name '%s', surname '%s' and age %d already exists.",
                            personDto.name(),
                            personDto.surname(),
                            personDto.age()
                    ));
        }

        City city = cityRepository.findCityByName(personDto.cityName()).orElseThrow(() -> new CityNotFoundException(
                String.format("City '%s' not found.", personDto.cityName())
        ));

        Person person = personMapper.dtoToPerson(personDto, city);
        personRepository.save(person);

        Person.PersonId personId = person.getId();
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{name}/{surname}/{age}")
                .buildAndExpand(personId.getName(), personId.getSurname(), personId.getAge())
                .toUri();
    }
}
