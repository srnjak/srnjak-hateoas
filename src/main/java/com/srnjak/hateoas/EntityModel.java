package com.srnjak.hateoas;

import lombok.Data;

import java.util.Collection;

@Data
public class EntityModel<T> extends HypermediaModel {
    private T content;

    public EntityModel(T content) {
        this.content = content;
    }

    public EntityModel(T content, Link... links) {
        super(links);
        this.content = content;
    }

    public EntityModel(T content, Collection<Link> links) {
        super(links);
        this.content = content;
    }
}
