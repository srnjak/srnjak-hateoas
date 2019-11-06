package com.srnjak.hateoas.hal;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

@Getter
public final class HalEmbeddedEntry {

    public static class Builder {
        private String name;
        private List<HalObject> halObjectList = new ArrayList<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder addObject(HalObject halObject) {
            this.halObjectList.add(halObject);
            return this;
        }

        public Builder addAll(Builder builder) {
            this.halObjectList.addAll(builder.halObjectList);
            return this;
        }

        public HalEmbeddedEntry buildSingle() {
            this.halObjectList.stream()
                    .skip(1)
                    .findAny()
                    .ifPresent(o -> {
                        throw new IllegalArgumentException(
                                "More than one object added");
                    });

            return new HalEmbeddedEntry(
                    this.name, this.halObjectList.iterator().next());
        }

        public HalEmbeddedEntry buildArray() {
            return new HalEmbeddedEntry(this.name, this.halObjectList);
        }
    }

    private final String name;
    private final HalObject halObject;
    private final List<HalObject> halObjectList;

    public HalEmbeddedEntry(String name, HalObject halObject) {
        this.name = name;
        this.halObject = halObject;
        this.halObjectList = null;
    }

    public HalEmbeddedEntry(String name, List<HalObject> halObjectList) {
        this.name = name;
        this.halObject = null;
        this.halObjectList = Collections.unmodifiableList(halObjectList);
    }

    public static Collector<HalObject, Builder, HalEmbeddedEntry> collector(
            String name) {

        return Collector.of(
                () -> HalEmbeddedEntry.builder(name),
                HalEmbeddedEntry::add,
                HalEmbeddedEntry::addAll,
                Builder::buildArray
        );
    }

    static void add(Builder builder, HalObject halObject) {
        builder.addObject(halObject);
    }

    static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }
}
