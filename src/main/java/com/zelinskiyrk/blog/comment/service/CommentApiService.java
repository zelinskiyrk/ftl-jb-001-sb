package com.zelinskiyrk.blog.comment.service;

import com.zelinskiyrk.blog.article.api.response.ArticleResponse;
import com.zelinskiyrk.blog.article.exception.ArticleNotExistException;
import com.zelinskiyrk.blog.article.repository.ArticleRepository;
import com.zelinskiyrk.blog.auth.exceptions.AuthException;
import com.zelinskiyrk.blog.auth.exceptions.NotAccessException;
import com.zelinskiyrk.blog.auth.service.AuthService;
import com.zelinskiyrk.blog.base.service.CheckAccess;
import com.zelinskiyrk.blog.comment.api.request.CommentSearchRequest;
import com.zelinskiyrk.blog.comment.mapping.CommentMapping;
import com.zelinskiyrk.blog.base.api.request.SearchRequest;
import com.zelinskiyrk.blog.base.api.response.SearchResponse;
import com.zelinskiyrk.blog.comment.api.request.CommentRequest;
import com.zelinskiyrk.blog.comment.exception.CommentExistException;
import com.zelinskiyrk.blog.comment.exception.CommentNotExistException;
import com.zelinskiyrk.blog.comment.model.CommentDoc;
import com.zelinskiyrk.blog.comment.repository.CommentRepository;
import com.zelinskiyrk.blog.user.exception.UserNotExistException;
import com.zelinskiyrk.blog.user.model.UserDoc;
import com.zelinskiyrk.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentApiService extends CheckAccess<CommentDoc> {
    private final CommentRepository commentRepository;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final AuthService authService;

    public CommentDoc create(CommentRequest request) throws ArticleNotExistException, AuthException {
        UserDoc userDoc = authService.currentUser();
        if (articleRepository.findById(request.getArticleId()).isPresent() == false) throw new ArticleNotExistException();
        CommentDoc commentDoc = CommentMapping.getInstance().getRequest().convert(request, userDoc.getId());
        commentRepository.save(commentDoc);
        return commentDoc;
    }

    public Optional<CommentDoc> findById(ObjectId id) {
        return commentRepository.findById(id);
    }

    public SearchResponse<CommentDoc> search(
            CommentSearchRequest request
    ) {
        List<Criteria> orCriterias = new ArrayList<>();

        if (request.getQuery() != null && request.getQuery() != "") {
            orCriterias.add(Criteria.where("message").regex(request.getQuery(), "i"));
        }

        if (request.getArticleId() != null){
            orCriterias.add(Criteria.where("articleId").is(request.getArticleId()));
        }

        if (request.getUserId() != null){
            orCriterias.add(Criteria.where("userId").is(request.getUserId()));
        }

        Criteria criteria = new Criteria();

        if (orCriterias.size() > 0){
            criteria = criteria.orOperator(
                orCriterias.toArray(new Criteria[orCriterias.size()])
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CommentDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<CommentDoc> commentDocs = mongoTemplate.find(query, CommentDoc.class);
        return SearchResponse.of(commentDocs, count);
    }

    public CommentDoc update(CommentRequest request) throws CommentNotExistException, AuthException, NotAccessException {
        Optional<CommentDoc> commentDocOptional = commentRepository.findById(request.getId());
        if (commentDocOptional.isPresent() == false) {
            throw new CommentNotExistException();
        }

        CommentDoc oldDoc = commentDocOptional.get();
        UserDoc owner = checkAccess(oldDoc);

        CommentDoc commentDoc = CommentMapping.getInstance().getRequest().convert(request, owner.getId());
        commentDoc.setId(request.getId());
        commentDoc.setId(oldDoc.getArticleId());
        commentDoc.setUserId(oldDoc.getUserId());
        commentRepository.save(commentDoc);

        return commentDoc;
    }

    public void delete(ObjectId id) throws AuthException, NotAccessException, ChangeSetPersister.NotFoundException {
        checkAccess(commentRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new));
        commentRepository.deleteById(id);
    }

    @Override
    protected ObjectId getOwnerFromEntity(CommentDoc entity) {
        return entity.getUserId();
    }

    @Override
    protected AuthService authService() {
        return authService;
    }
}
