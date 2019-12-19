package com.srnjak.hateoas.utils;

import lombok.Value;

import java.lang.reflect.Type;

@Value
public class GenericEntityWrapper {
    Object entity;
    Type genericType;
}
