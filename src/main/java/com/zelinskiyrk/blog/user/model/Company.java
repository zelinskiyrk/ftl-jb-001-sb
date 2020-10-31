package com.zelinskiyrk.blog.user.model;

import lombok.*;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String name;
    private Address address;
}
