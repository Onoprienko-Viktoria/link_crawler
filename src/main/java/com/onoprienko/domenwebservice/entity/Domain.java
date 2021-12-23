package com.onoprienko.domenwebservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Domain {
    private String name;
    private double allLinks;
    private double internalLinks;
    private double externalLinks;
}
