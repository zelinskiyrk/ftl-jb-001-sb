package com.zelinskiyrk.blog.base.mapping;

public abstract class BaseMapping<From, To> {
    public abstract To convert(From from);
    public abstract From unmapping(To to);
}
