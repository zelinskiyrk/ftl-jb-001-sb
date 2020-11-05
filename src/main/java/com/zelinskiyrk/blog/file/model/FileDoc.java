package com.zelinskiyrk.blog.file.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDoc {
    @Id
    private ObjectId id;
    private String title;
    private ObjectId ownerId;
}
