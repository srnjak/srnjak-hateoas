package com.srnjak.hateoas.mediatype.hal.jaxrs;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
public class TestEntity {
    String id;
    String name;
    Integer age;
    boolean enabled;
}
