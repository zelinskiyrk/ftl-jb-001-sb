package com.zelinskiyrk.blog.comment.routes;

import com.zelinskiyrk.blog.base.routes.BaseApiRoutes;

public class CommentApiRoutes {
    public static final String ROOT = BaseApiRoutes.v1 + "/comment";
    public static final String BY_ID = ROOT + "/{id}";
}
