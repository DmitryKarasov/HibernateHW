package com.karasov.hibernatehw.repository;

import com.karasov.hibernatehw.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Person}.
 * Этот интерфейс расширяет {@link JpaRepository}, предоставляя основные операции CRUD для сущности {@link Person}.
 * Включает дополнительные методы для поиска, обновления и удаления людей по различным критериям.
 */
public interface PersonRepository extends JpaRepository<Person, Person.PersonId> {

    /**
     * Находит человека по уникальному идентификатору {@link Person.PersonId}.
     *
     * @param personId Уникальный идентификатор человека.
     * @return {@link Optional} с найденным человеком, если таковой существует.
     */
    Optional<Person> findPersonById(Person.PersonId personId);

    /**
     * Обновляет номер телефона и идентификатор города человека по его уникальному идентификатору.
     *
     * @param personId    Уникальный идентификатор человека.
     * @param phoneNumber Новый номер телефона.
     * @param cityId      Идентификатор города.
     * @return Количество обновленных записей.
     */
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE person SET \
            phone_number = :phoneNumber, city_id = :cityId \
            WHERE name = :#{#personId.name} AND surname = :#{#personId.surname} AND age = :#{#personId.age} \
            """, nativeQuery = true)
    int updatePersonById(Person.PersonId personId, String phoneNumber, Long cityId);

    /**
     * Находит людей, проживающих в указанном городе.
     *
     * @param city Название города.
     * @return Список людей, проживающих в указанном городе.
     */
    @Query(value = """
                 SELECT\s
                     p.name,
                     p.surname,
                     p.age,
                     p.phone_number,
                     p.city_id
                 FROM person p
                 JOIN city c ON p.city_id = c.id
                 WHERE LOWER(c.name) = LOWER(:city)
            \s""",
            nativeQuery = true)
    List<Person> getPersonsByCity(@Param("city") String city);

    /**
     * Находит людей, возраст которых меньше указанного, отсортированных по возрастанию возраста.
     *
     * @param age Возраст, по которому будет происходить фильтрация.
     * @return Список людей, возраст которых меньше указанного.
     */
    @Query(
            value = """
                    SELECT * FROM person
                    WHERE age < :age;
                    """,
            nativeQuery = true
    )
    List<Person> getPersonByAgeLowerThanAsc(@Param("age") int age);

    /**
     * Находит человека по имени и фамилии.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @return {@link Optional} с найденным человеком, если таковой существует.
     */
    @Query(
            value = """
                    SELECT * FROM person
                    WHERE LOWER(name) = LOWER(:name) AND LOWER(surname) = LOWER(:surname);
                    """,
            nativeQuery = true
    )
    Optional<Person> getPersonByNameAndSurname(@Param("name") String name,
                                               @Param("surname") String surname);

    /**
     * Удаляет человека по ключу: имени, фамилии и возрасту.
     *
     * @param name    Имя человека.
     * @param surname Фамилия человека.
     * @param age     Возраст человека.
     */
    @Modifying
    @Transactional
    @Query(
            value = """
                    DELETE FROM person
                    WHERE LOWER(name) = LOWER(:name) AND LOWER(surname) = LOWER(:surname) AND age = :age;
                    """,
            nativeQuery = true
    )
    void deletePerson(@Param("name") String name,
                      @Param("surname") String surname,
                      @Param("age") int age);
}
