package com.srnjak.hateoas.examples.relations;

import com.srnjak.hateoas.relation.CurieDefinition;
import com.srnjak.hateoas.relation.CurieLinkRelation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestCurieRelation implements CurieLinkRelation {

    public static final CurieDefinition CURIE_DEFINITION =
            CurieDefinition.builder()
                    .prefix("ts")
                    .href("http://www.test.com/")
                    .build();

    public static final TestCurieRelation SAMPLE =
            new TestCurieRelation("sample");

    private String reference;

    @Override
    public CurieDefinition getCurie() {
        return TestCurieRelation.CURIE_DEFINITION;
    }

    @Override
    public String getReference() {
        return this.reference;
    }
}
