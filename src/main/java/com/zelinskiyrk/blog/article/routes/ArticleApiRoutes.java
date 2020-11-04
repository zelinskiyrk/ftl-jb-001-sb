package com.zelinskiyrk.blog.article.routes;

import com.zelinskiyrk.blog.base.routes.BaseApiRoutes;

public class ArticleApiRoutes {
    public static final String ROOT = BaseApiRoutes.v1 + "/article";
    public static final String BY_ID = ROOT + "/{id}";
}
