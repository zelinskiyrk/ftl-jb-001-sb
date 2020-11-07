package com.zelinskiyrk.blog.photo.repository;

import com.zelinskiyrk.blog.photo.model.PhotoDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends MongoRepository<PhotoDoc, ObjectId> {
}
