package com.srnjak.hateoas;

import lombok.Data;

@Data
public class DefaultRelationType implements RelationType {
    private String uri;

    @Override
    public String name() {
        return this.uri;
    }
}
