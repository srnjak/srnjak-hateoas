package com.srnjak.hateoas;

import lombok.Data;

@Data
public class CurieTarget {
    private String href;
    private Boolean templated;
}
