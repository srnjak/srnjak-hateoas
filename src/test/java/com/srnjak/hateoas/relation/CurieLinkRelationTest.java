package com.srnjak.hateoas.relation;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class CurieLinkRelationTest {

    private CurieLinkRelation curieLinkRelation = new CurieLinkRelation() {
        @Override
        public CurieDefinition getCurie() {
            return CurieDefinition.builder()
                    .href("http://www.example.com/rel/")
                    .prefix("pfx")
                    .build();
        }

        @Override
        public String getReference() {
            return "test";
        }
    };

    @Test
    public void extractValue() {
        assertEquals("pfx:test", curieLinkRelation.getValue());
    }

    @Test
    public void extractUri() {
        assertEquals(
                URI.create("http://www.example.com/rel/test"),
                curieLinkRelation.getUri());
    }

}