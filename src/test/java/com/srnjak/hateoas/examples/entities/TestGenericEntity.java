package com.srnjak.hateoas.examples.entities;

import com.srnjak.hateoas.examples.entities.TestEntity;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({TestEntity.class})
@Data
public class TestGenericEntity<E> {
    String id;
    E genericProperty;
}
