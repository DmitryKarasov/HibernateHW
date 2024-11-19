package com.karasov.hibernatehw.controller;

import com.karasov.hibernatehw.entity.Person;
import com.karasov.hibernatehw.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    @GetMapping("/by-city")
    public ResponseEntity<List<Person>> getPersonListByCityName(@RequestParam String city) {
        return ResponseEntity.ok(service.getPersonsByCity(city));
    }
}
