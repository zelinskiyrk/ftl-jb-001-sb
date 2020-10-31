package com.zelinskiyrk.blog.user.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;
    private String street;
    private String suite;
    private String zipcode;
    private Point point;
}
