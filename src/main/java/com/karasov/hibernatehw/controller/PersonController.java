package com.karasov.hibernatehw.controller;

import com.karasov.hibernatehw.dto.PatchPersonDto;
import com.karasov.hibernatehw.dto.PersonDto;
import com.karasov.hibernatehw.entity.Person;
import com.karasov.hibernatehw.handler.exception.CityNotFoundException;
import com.karasov.hibernatehw.handler.exception.CreatingPersonAlreadyExistsException;
import com.karasov.hibernatehw.handler.exception.PersonNotFoundException;
import com.karasov.hibernatehw.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * Контроллер для работы с сущностью {@link Person}.
 * Предоставляет REST API для создания, чтения, обновления и удаления людей.
 * Все операции взаимодействуют с сервисом {@link PersonService}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    /**
     * Получает информацию о человеке по его имени, фамилии и возрасту.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @param age     Возраст человека.
     * @return {@link ResponseEntity} с объектом {@link PersonDto}, если человек найден.
     * @throws PersonNotFoundException если человек не найден.
     */
    @GetMapping("/{name}/{surname}/{age}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable String name,
                                               @PathVariable String surname,
                                               @PathVariable int age
    ) {
        return ResponseEntity.ok(service.getPerson(name, surname, age));
    }

    /**
     * Получает список людей, проживающих в указанном городе.
     *
     * @param city Название города.
     * @return {@link ResponseEntity} со списком объектов {@link PersonDto}.
     * @throws CityNotFoundException если город не найден.
     */
    @GetMapping("/by-city")
    public ResponseEntity<List<PersonDto>> getPersonListByCityName(@RequestParam String city) {
        return ResponseEntity.ok(service.getPersonsByCity(city));
    }

    /**
     * Получает список людей, возраст которых меньше указанного, отсортированных по возрастанию возраста.
     *
     * @param age Возраст для фильтрации.
     * @return {@link ResponseEntity} со списком объектов {@link PersonDto}.
     */
    @GetMapping("/by-age")
    public ResponseEntity<List<PersonDto>> getPersonListByCityName(@RequestParam int age) {
        return ResponseEntity.ok(service.getPersonByAgeLowerThanAsc(age));
    }

    /**
     * Получает информацию о человеке по его имени и фамилии.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @return {@link ResponseEntity} с объектом {@link PersonDto}, если человек найден.
     * @throws PersonNotFoundException если человек не найден.
     */
    @GetMapping()
    public ResponseEntity<PersonDto> getPersonByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname
    ) {
        return ResponseEntity.ok(service.getPersonByNameAndSurname(name, surname));
    }

    /**
     * Удаляет человека по его ключу: имени, фамилии и возрасту.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @param age     Возраст человека.
     * @return {@link ResponseEntity} с статусом 200 OK, если человек удален.
     * @throws PersonNotFoundException если человек не найден.
     */
    @DeleteMapping()
    public ResponseEntity<Void> deletePerson(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam int age
    ) {
        service.deletePerson(name, surname, age);
        return ResponseEntity.ok().build();
    }

    /**
     * Создает нового человека.
     *
     * @param personDto Данные для создания нового человека.
     * @return {@link ResponseEntity} с URI нового созданного человека и статусом 201 Created.
     * @throws CreatingPersonAlreadyExistsException если человек с такими данными уже существует.
     * @throws CityNotFoundException                если указанный город не найден.
     */
    @PostMapping()
    public ResponseEntity<URI> createPerson(@RequestBody PersonDto personDto) {
        return ResponseEntity.created(service.createPerson(personDto)).build();
    }

    /**
     * Обновляет данные человека (номер телефона и город).
     *
     * @param name           Имя человека.
     * @param surname        Фамилия человека.
     * @param age            Возраст человека.
     * @param patchPersonDto Данные для обновления.
     * @return {@link ResponseEntity} с количеством обновленных записей.
     * @throws PersonNotFoundException если человек не найден.
     * @throws CityNotFoundException   если указанный город не найден.
     */
    @PatchMapping("/{name}/{surname}/{age}")
    public ResponseEntity<Integer> updatePerson(@PathVariable String name,
                                                @PathVariable String surname,
                                                @PathVariable int age,
                                                @RequestBody PatchPersonDto patchPersonDto) {
        return ResponseEntity.ok(service.patchPerson(name, surname, age, patchPersonDto));
    }
}
