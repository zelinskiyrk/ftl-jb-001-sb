package com.zelinskiyrk.blog.base.service;

import com.zelinskiyrk.blog.auth.exceptions.AuthException;
import com.zelinskiyrk.blog.auth.exceptions.NotAccessException;
import com.zelinskiyrk.blog.auth.service.AuthService;
import com.zelinskiyrk.blog.user.model.UserDoc;
import org.bson.types.ObjectId;

public abstract class CheckAccess<T> {


    protected abstract ObjectId getOwnerFromEntity(T entity);

    protected UserDoc checkAccess(T entity) throws AuthException, NotAccessException {
        ObjectId ownerId = getOwnerFromEntity(entity);

        UserDoc owner = authService().currentUser();

        if (owner.getId().equals(ownerId) == false) throw new NotAccessException();

        return owner;
    }

    protected abstract AuthService authService();
}
