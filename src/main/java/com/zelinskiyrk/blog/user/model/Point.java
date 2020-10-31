package com.zelinskiyrk.blog.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Point {
    private Double lat;
    private Double lng;
}
