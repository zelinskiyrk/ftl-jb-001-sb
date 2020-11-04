package com.zelinskiyrk.blog.comment.repository;

import com.zelinskiyrk.blog.comment.model.CommentDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<CommentDoc, ObjectId> {
}
