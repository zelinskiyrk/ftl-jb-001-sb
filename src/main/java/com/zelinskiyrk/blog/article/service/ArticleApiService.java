package com.zelinskiyrk.blog.article.service;

import com.zelinskiyrk.blog.article.mapping.ArticleMapping;
import com.zelinskiyrk.blog.auth.exceptions.AuthException;
import com.zelinskiyrk.blog.auth.exceptions.NotAccessException;
import com.zelinskiyrk.blog.auth.service.AuthService;
import com.zelinskiyrk.blog.base.api.request.SearchRequest;
import com.zelinskiyrk.blog.base.api.response.SearchResponse;
import com.zelinskiyrk.blog.article.api.request.ArticleRequest;
import com.zelinskiyrk.blog.article.exception.ArticleExistException;
import com.zelinskiyrk.blog.article.exception.ArticleNotExistException;
import com.zelinskiyrk.blog.article.model.ArticleDoc;
import com.zelinskiyrk.blog.article.repository.ArticleRepository;
import com.zelinskiyrk.blog.base.service.CheckAccess;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleApiService extends CheckAccess<ArticleDoc> {
    private final ArticleRepository articleRepository;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final AuthService authService;

    public ArticleDoc create(ArticleRequest request) throws AuthException {
        UserDoc userDoc = authService.currentUser();

        ArticleDoc articleDoc = ArticleMapping.getInstance().getRequest().convert(request, userDoc.getId());
        articleRepository.save(articleDoc);
        return articleDoc;
    }

    public Optional<ArticleDoc> findById(ObjectId id) {
        return articleRepository.findById(id);
    }

    public SearchResponse<ArticleDoc> search(
            SearchRequest request
    ) {
        Criteria criteria = new Criteria();
        if (request.getQuery() != null && request.getQuery() != "") {
            criteria = criteria.orOperator(
                    // TODO: Add Criteria
                    Criteria.where("title").regex(request.getQuery(), "i"),
                    Criteria.where("body").regex(request.getQuery(), "i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, ArticleDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<ArticleDoc> articleDocs = mongoTemplate.find(query, ArticleDoc.class);
        return SearchResponse.of(articleDocs, count);
    }

    public ArticleDoc update(ArticleRequest request) throws ArticleNotExistException, AuthException, NotAccessException {
        Optional<ArticleDoc> articleDocOptional = articleRepository.findById(request.getId());
        if (articleDocOptional.isPresent() == false) {
            throw new ArticleNotExistException();
        }

        ArticleDoc oldDoc = articleDocOptional.get();
        UserDoc owner = checkAccess(oldDoc);

        ArticleDoc articleDoc = ArticleMapping.getInstance().getRequest().convert(request, owner.getId());
        articleDoc.setId(request.getId());
        articleDoc.setOwnerId(oldDoc.getOwnerId());
        articleRepository.save(articleDoc);

        return articleDoc;
    }

    public void delete(ObjectId id) throws AuthException, NotAccessException, ChangeSetPersister.NotFoundException {
        checkAccess(articleRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new));
        articleRepository.deleteById(id);
    }

    @Override
    protected ObjectId getOwnerFromEntity(ArticleDoc entity) {
        return entity.getOwnerId();
    }

    @Override
    protected AuthService authService() {
        return authService;
    }
}
