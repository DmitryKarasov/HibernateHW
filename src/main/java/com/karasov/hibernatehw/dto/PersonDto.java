package com.karasov.hibernatehw.dto;

public record PersonDto(
        String name,
        String surname,
        int age,
        String phoneNumber,
        String cityName
) {
}
