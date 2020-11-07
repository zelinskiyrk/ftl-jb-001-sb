package com.zelinskiyrk.blog.photo.model;

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
public class PhotoDoc {
    @Id
            private ObjectId id;
            private String title;
            private ObjectId ownerId;
            private ObjectId albumId;
            private String contentType;
}
