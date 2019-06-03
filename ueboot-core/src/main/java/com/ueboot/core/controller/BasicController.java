package com.ueboot.core.controller;

/**
 * @author felix
 */
public class BasicController<T> {
    protected static final String ADD = "add";
    protected static final String ADD_CH = "新增";
    protected static final String UPDATE = "update";
    protected static final String UPDATE_CH = "修改";
    protected static final String DELETE = "delete";
    protected static final String DELETE_CH = "删除";
    protected static final String SINGLE = "single";
    protected static final String SINGLE_CH = "查询单条";
    protected static final String LIST = "list";
    protected static final String LIST_CH = "列表查询";
    protected static final String PAGE = "page";
    protected static final String PAGE_CH = "分页查询";
    protected static final int LOG_INSERT = 1;
    protected static final int LOG_UPDATE = 2;
    protected static final int LOG_DELETE = 3;
    protected static final int LOG_SELECT = 4;
    protected static final String AUTH_YES = "鉴权: 是";
    protected static final String AUTH_NO = "鉴权: 否";
    protected static final String POST = "POST";
    protected static final String GET = "GET";
    protected final ConverterDtv<T> converterDTV = new ConverterDtv<>();
}
