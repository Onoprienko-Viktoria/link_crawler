package com.onoprienko.domenwebservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Link {
    private String domainName;
    private String url;
    private int nestingLevel;
    private int externalLinks;
}
