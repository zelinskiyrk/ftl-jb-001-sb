package com.zelinskiyrk.blog.user.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.DigestUtils;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDoc {
    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Company company = new Company();
    private Address address = new Address();

    private Integer failLogin = 0;

    public static String hexPassword(String clearPassword){
        return DigestUtils.md5DigestAsHex(clearPassword.getBytes());
    }
}
