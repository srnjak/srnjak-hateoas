package com.srnjak.hateoas.examples.entities;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
public class TestEntity {
    String id;
    String name;
    Integer age;
    boolean enabled;
}
