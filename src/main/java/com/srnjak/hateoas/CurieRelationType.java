package com.srnjak.hateoas;

import lombok.Data;

@Data
public class CurieRelationType implements RelationType {
    private String name;
    private CurieTarget target;

    @Override
    public String name() {
        return this.name;
    }
}
