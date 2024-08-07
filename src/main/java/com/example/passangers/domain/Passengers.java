package com.example.passangers.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passangers")
@ToString
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "survived")
    @Convert(converter = NumericBooleanConverter.class)
    public Boolean survived;

    @Column(name = "name")
    public String name;

    @Column(name = "age")
    public Integer age;

    @Column(name = "pclass")
    @Enumerated(EnumType.STRING)
    public Pclass pclass;

    @Column(name = "sex")
    public String sex;

    @Column(name = "siblings_aboard")
    public Integer siblings_aboard;

    @Column(name = "children_aboard")
    public Integer children_aboard;

    @Column(name = "fare")
    public Double fare;




    public Passengers(boolean survived, String pclass, String name, String sex, int age, int siblings_aboard, int children_aboard, double fare) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passengers)) return false;
        Passengers that = (Passengers) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
