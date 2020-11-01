package com.zelinskiyrk.blog.user.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Address", description = "User or company address")
public class Address {
    private String city;
    private String street;
    private String suite;
    private String zipcode;
    private Point point;
}
