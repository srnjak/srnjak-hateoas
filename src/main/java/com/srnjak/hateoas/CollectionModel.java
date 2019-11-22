package com.srnjak.hateoas;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class CollectionModel<T extends EntityModel<?>> extends HypermediaModel {

    private List<T> content = new ArrayList<>();

    public CollectionModel(Collection<T> entities) {
        this.content.addAll(entities);
    }

    public CollectionModel(Collection<T> entities, Link... links) {
        super(links);
        this.content.addAll(entities);
    }

    public CollectionModel(Collection<T> entities, Collection<Link> links) {
        super(links);
        this.content.addAll(entities);
    }

    public void add(T entity) {
        this.content.add(entity);
    }
}
