package com.srnjak.hateoas.hal;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

@Getter
public final class HalLinkEntry {

    public static class Builder {
        private String rel;
        private List<HalLink> halLinkList = new ArrayList<>();

        public Builder rel(String rel) {
            this.rel = rel;
            return this;
        }

        public Builder addLink(HalLink halLink) {
            this.halLinkList.add(halLink);
            return this;
        }

        public Builder addAll(Builder builder) {
            builder.halLinkList.forEach(l -> this.halLinkList.add(l));
            return this;
        }

        public HalLinkEntry buildSingle() {
            this.halLinkList.stream()
                    .skip(1)
                    .findAny()
                    .ifPresent(o -> {
                        throw new IllegalArgumentException(
                                "More than one link added.");
                    });

            return new HalLinkEntry(
                    this.rel, this.halLinkList.iterator().next());
        }

        public HalLinkEntry buildArray() {
            return new HalLinkEntry(this.rel, this.halLinkList);
        }
    }

    public static Collector<HalLink, Builder, HalLinkEntry> collector(
            String rel) {

        return Collector.of(
                () -> HalLinkEntry.builder().rel(rel),
                HalLinkEntry::addLink,
                HalLinkEntry::addAll,
                Builder::buildArray
        );
    }

    static void addLink(Builder builder, HalLink halLink) {
        builder.addLink(halLink);
    }

    static Builder addAll(Builder builder, Builder builder2) {
        return builder.addAll(builder2);
    }

    public static Builder builder() {
        return new Builder();
    }

    private final String rel;
    private final HalLink halLink;

    private final List<HalLink> halLinkList;

    public HalLinkEntry(String rel, HalLink halLink) {
        this.rel = rel;
        this.halLink = halLink;
        this.halLinkList = null;
    }

    public HalLinkEntry(String rel, List<HalLink> halLinkList) {
        this.rel = rel;
        this.halLink = null;
        this.halLinkList = Collections.unmodifiableList(halLinkList);
    }
}
