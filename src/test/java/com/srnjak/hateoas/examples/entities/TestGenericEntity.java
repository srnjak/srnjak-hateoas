package com.srnjak.hateoas.examples.entities;

import com.srnjak.hateoas.examples.entities.TestEntity;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({TestEntity.class})
@Data
public class TestGenericEntity<E> {
    String id;
    E genericProperty;
}
