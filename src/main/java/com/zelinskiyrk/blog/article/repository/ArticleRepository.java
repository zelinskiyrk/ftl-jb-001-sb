package com.zelinskiyrk.blog.article.repository;

import com.zelinskiyrk.blog.article.model.ArticleDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends MongoRepository<ArticleDoc, ObjectId> {
}
