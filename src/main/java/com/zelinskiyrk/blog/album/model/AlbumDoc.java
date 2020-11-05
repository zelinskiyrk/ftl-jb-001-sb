package com.zelinskiyrk.blog.album.model;

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
public class AlbumDoc {
    @Id
            private ObjectId id;
            private String title;
            private ObjectId ownerId;
}
