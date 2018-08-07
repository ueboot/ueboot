package com.ueboot.core.jpa.repository.query;

import org.hibernate.type.Type;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangli.ma
 * @since 1.0
 */
public class StringQuery {

    private final static String SPACE = " ";

    private StringBuilder stringQuery;

    private NamedParams namedParams;

    private boolean predicate;

    private Map<String, Type> scalars;

    private StringQuery() {
        stringQuery = new StringBuilder();
        namedParams = NamedParams.newParams();
        scalars = new HashMap<>();
        predicate = true;
    }

    public static StringQuery newQuery() {
        return new StringQuery();
    }

    public StringQuery query(String query) {
        if (this.predicate) {
            this.stringQuery.append(query);
        }
        return this;
    }

    public StringQuery param(String paramName, Object paramValue) {
        if (this.predicate) {
            namedParams.param(paramName, paramValue);
        }

        return this;
    }
    public StringQuery space() {
        if (this.predicate) {
            this.stringQuery.append(SPACE);
        }

        return this;
    }

    public StringQuery likeParam(String paramName, Object paramValue) {
        if (this.predicate) {
            namedParams.likeParam(paramName, paramValue);
        }

        return this;
    }

    public StringQuery likeEndParam(String paramName, Object paramValue) {
        if (this.predicate) {
            namedParams.likeEndParam(paramName, paramValue);
        }

        return this;
    }

    public StringQuery likeStartParam(String paramName, Object paramValue) {
        if (this.predicate) {
            namedParams.likeStartParam(paramName, paramValue);
        }
        return this;
    }

    public StringQuery inParam(String paramName, Object paramValue) {
        if (this.predicate) {
            namedParams.inParam(paramName, paramValue);
        }

        return this;
    }

    public StringQuery addScalar(String s, Type type) {
        if (this.predicate) {
            this.scalars.put(s, type);
        }
        return this;
    }

    public StringQuery predicate(boolean predicate) {
        this.predicate = predicate;
        return this;
    }

    public StringQuery predicateNotNull(Object o) {
        if (o == null) {
            this.predicate = false;
        } else {
            this.predicate = true;
        }
        return this;
    }

    public StringQuery predicateIsNull(Object o) {
        if (o == null) {
            this.predicate = true;
        } else {
            this.predicate = false;
        }
        return this;
    }

    public StringQuery predicateHasText(String o) {
        if (StringUtils.hasText(o)) {
            this.predicate = true;
        } else {
            this.predicate = false;
        }
        return this;
    }

    public StringQuery predicateNotHasText(String o) {
        if (StringUtils.hasText(o)) {
            this.predicate = false;
        } else {
            this.predicate = true;
        }
        return this;
    }

    public StringQuery predicateNotEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            this.predicate = false;
        } else {
            this.predicate = true;
        }
        return this;
    }

    public StringQuery predicateIsEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            this.predicate = true;
        } else {
            this.predicate = false;
        }
        return this;
    }

    public StringQuery predicateNotEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            this.predicate = false;
        } else  {
            this.predicate = true;
        }
        return this;
    }

    public StringQuery predicateIsEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            this.predicate = true;
        } else  {
            this.predicate = false;
        }
        return this;
    }

    public StringQuery build() {
        this.predicate = true;
        return this;
    }

    public String getQuery() {
        return this.stringQuery.toString();
    }

    public Map<String, Type> getScalars() {
        return this.scalars;
    }

    public NamedParams getParams() {
        return this.namedParams;
    }


}
