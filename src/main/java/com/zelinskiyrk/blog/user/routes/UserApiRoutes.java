package com.zelinskiyrk.blog.user.routes;

import com.zelinskiyrk.blog.base.routes.BaseApiRoutes;

public class UserApiRoutes {
    public static final String ROOT = BaseApiRoutes.v1 + "/user";
    public static final String BY_ID = ROOT + "/{id}";
}
