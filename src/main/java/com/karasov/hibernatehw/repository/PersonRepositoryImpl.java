package com.karasov.hibernatehw.repository;

import com.karasov.hibernatehw.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PersonRepositoryImpl implements PersonRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Person> getPersonsByCity(String city) {
        String sql = """
                SELECT p.*
                FROM person p
                JOIN city c ON p.city_id = c.id
                WHERE LOWER(c.name) = LOWER(:city)
                """;

        return entityManager
                .createNativeQuery(sql, Person.class)
                .setParameter("city", city)
                .getResultList();
    }
}
