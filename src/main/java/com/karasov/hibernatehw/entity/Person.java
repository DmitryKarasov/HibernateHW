package com.karasov.hibernatehw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {

    @EmbeddedId
    private PersonId id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City cityOfLiving;

    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class PersonId implements Serializable {

        @Column(name = "name")
        private String name;

        @Column(name = "surname")
        private String surname;

        @Column(name = "age")
        private int age;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonId personId = (PersonId) o;
            return age == personId.age &&
                    Objects.equals(name, personId.name) &&
                    Objects.equals(surname, personId.surname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, surname, age);
        }
    }
}

