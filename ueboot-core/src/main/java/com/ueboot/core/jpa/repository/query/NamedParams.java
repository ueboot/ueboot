package com.ueboot.core.jpa.repository.query;


import org.springframework.util.Assert;

import java.util.*;

/**
 * @author xiangli.ma
 * @since 1.0
 */
public class NamedParams {

    private Map<String, Object> parameters = new HashMap<String, Object>();

    private NamedParams() {

    }

    public static NamedParams newParams() {
        return new NamedParams();
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public NamedParams param(String paramName, Object paramValue) {
        Assert.notNull(paramName, "Query paramName must not be null!");
        Assert.notNull(paramValue, "Query paramValue must not be null!");

        parameters.put(paramName, paramValue);
        return this;
    }

    public NamedParams likeParam(String paramName, Object paramValue) {
        Assert.notNull(paramName, "Query paramName must not be null!");
        Assert.notNull(paramValue, "Query paramValue must not be null!");

        String paramValueStr = String.valueOf(paramValue);
        Assert.hasText(paramValueStr, "Like paramValue must has text");

        parameters.put(paramName, "%" + paramValueStr + "%");
        return this;
    }

    public NamedParams likeStartParam(String paramName, Object paramValue) {
        Assert.notNull(paramName, "Query paramName must not be null!");
        Assert.notNull(paramValue, "Query paramValue must not be null!");

        String paramValueStr = String.valueOf(paramValue);
        Assert.hasText(paramValueStr, "Like paramValue must has text");

        parameters.put(paramName, paramValueStr + "%");
        return this;
    }

    public NamedParams likeEndParam(String paramName, Object paramValue) {
        Assert.notNull(paramName, "Query paramName must not be null!");
        Assert.notNull(paramValue, "Query paramValue must not be null!");

        String paramValueStr = String.valueOf(paramValue);
        Assert.hasText(paramValueStr, "Like paramValue must has text");

        parameters.put(paramName, "%" + paramValueStr);
        return this;
    }

    public NamedParams inParam(String paramName, Object paramValue) {
        Assert.notNull(paramName, "Query paramName must not be null!");
        Assert.notNull(paramValue, "Query paramValue must not be null!");


        if (paramValue.getClass().isArray() || paramValue instanceof Collection) {
            parameters.put(paramName, paramValue);
        } else {
            List paramList = new ArrayList();
            paramList.add(paramValue);
            parameters.put(paramName, paramList);
        }
        return this;
    }




}
