package com.zelinskiyrk.blog.user.model;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@ApiModel(value = "Point", description = "Coordinates")
public class Point {
    private Double lat;
    private Double lng;
}
