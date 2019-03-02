package com.ueboot.core.jpa.repository;

import com.ueboot.core.jpa.repository.query.NamedParams;
import com.ueboot.core.jpa.repository.query.QueryUtils;
import com.ueboot.core.jpa.repository.query.StringQuery;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.DefaultJpaEntityMetadata;
import org.springframework.data.jpa.repository.query.JpaEntityMetadata;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 默认使用StringQuery方式实现JPA查询，实现复杂的SQL查询
 *
 * @author yangkui
 * @author xiangli.ma
 * @since 1.0
 */
public class DefaultJpaRepository<T, ID extends Serializable> {

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";

    @PersistenceContext
    protected EntityManager em;

    protected JpaEntityMetadata<T> entityMetadata;

    public DefaultJpaRepository() {
        if (!(ParameterizedType.class.isAssignableFrom(super.getClass()
                .getGenericSuperclass().getClass()))) {
            return;
        }
        Type[] actualTypeArguments = ((ParameterizedType) super.getClass()
                .getGenericSuperclass()).getActualTypeArguments();
        this.entityMetadata = new DefaultJpaEntityMetadata((Class<T>) actualTypeArguments[0]);
    }

    protected Class<T> getDomainClass() {
        return entityMetadata.getJavaType();
    }


    private void setQueryParams(Query query, NamedParams params) {
        if (params.isEmpty()) {
            return;
        }

        Map<String, Object> parameters = params.getParameters();
        for (String paramName : parameters.keySet()) {
            query.setParameter(paramName, parameters.get(paramName));
        }

    }

    /**
     * 返回对象为Object类型，不指定具体的对象
     *
     * @param queryString 查询语句
     * @return 返回Object 对象
     */
    public List<Object> findObject(String queryString) {
        return findObject(queryString, NamedParams.newParams());
    }

    /**
     * 返回对象为Object类型，不指定具体的对象
     *
     * @param stringQuery 查询语句
     * @return 返回Object 对象
     */
    public List<Object> findObject(StringQuery stringQuery) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();
        return findObject(query, params);
    }


    public <S> List<S> find(StringQuery stringQuery, Class<S> transformer) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();
        return find(query, params, transformer);
    }

    public List<Object> findObject(String queryString, NamedParams params) {
        Assert.notNull(queryString, "Query must not be null!");
        Assert.notNull(params, "NamedParams must not be null!");

        Query query = em.createQuery(queryString);
        setQueryParams(query, params);
        return query.getResultList();
    }

    public <S> List<S> find(StringQuery stringQuery) {
        Assert.notNull(stringQuery, "Query must not be null!");
        String queryString = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();

        Assert.hasText(queryString, "Query must has text!");
        Assert.notNull(params, "QueryParams must not be null!");

        Query query = em.createQuery(queryString);
        setQueryParams(query, params);

        return query.getResultList();

    }

    public <S> List<S> find(String queryString, NamedParams params, Class<S> transformer) {
        Assert.notNull(queryString, "Query must not be null!");
        Assert.notNull(params, "NamedParams must not be null!");
        Assert.notNull(transformer, "Transformer Class must not be null");

        Query query = em.createQuery(queryString);
        setQueryParams(query, params);
        return query.unwrap(QueryImpl.class).setResultTransformer(Transformers.aliasToBean(transformer)).list();
    }


    public Page<T> findByPage(StringQuery stringQuery, Pageable pageable) {
        String queryString = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();
        String queryCount = QueryUtils.genCountQueryString(queryString);
        return getPage(pageable, queryString, params, queryCount);
    }

    private Page<T> getPage(Pageable pageable, String queryString, NamedParams params, String queryCount) {
        Assert.hasText(queryString, "Query must has text!");
        Assert.hasText(queryCount, "Query count must has text!");
        Assert.notNull(params, "QueryParams must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");

        Query query = em.createQuery(queryString);

        setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(Integer.valueOf(pageable.getOffset() + ""));

        List<T> resultList = query.getResultList();

        Query countQuery = em.createQuery(queryCount);
        setQueryParams(countQuery, params);
        Long total = (Long) countQuery.getSingleResult();

        Page page = new PageImpl(resultList, pageable, total);
        return page;
    }

    public Page<T> find(StringQuery stringQuery, Pageable pageable) {
        Assert.notNull(stringQuery, "StringQuery must not be null");
        Sort sort = pageable.getSort();
        if (sort != null) {
            int count = 0;
            Iterator<Sort.Order> it = sort.iterator();
            //自动拼接order by 属性
            while (it.hasNext()) {
                Sort.Order s = it.next();
                String name = s.getProperty();
                String dir = s.getDirection().name();
                if (count == 0) {
                    stringQuery.predicate(true)
                            .query(" order by " + name + " " + dir);
                } else {
                    stringQuery.predicate(true)
                            .query(", " + name + " " + dir);
                }
                count++;
            }
        }

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();
        return find(query, params, pageable);
    }

    public <S> Page<S> find(StringQuery stringQuery, Pageable pageable, Class<S> transformer) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");
        Assert.notNull(transformer, "TransformerClass must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();

        return find(query, params, pageable, transformer);
    }

    public <S> Page<S> find(StringQuery stringQuery, Pageable pageable, ResultTransformer transformer) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");
        Assert.notNull(transformer, "Transformer must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();

        return find(query, params, pageable, transformer);
    }

    public Page<T> find(String queryString, NamedParams params, Pageable pageable) {
        Assert.hasText(queryString, "Query must has text!");

        String queryCount = QueryUtils.genCountQueryString(queryString);
        return find(queryString, queryCount, params, pageable);
    }

    public <S> Page<S> find(String queryString, NamedParams params, Pageable pageable, Class<S> transformere) {
        Assert.hasText(queryString, "Query must has text!");

        String queryCount = QueryUtils.genCountQueryString(queryString);
        return find(queryString, queryCount, params, pageable, transformere);
    }

    public <S> Page<S> find(String queryString, NamedParams params, Pageable pageable, ResultTransformer transformer) {
        Assert.hasText(queryString, "Query must has text!");

        String queryCount = QueryUtils.genCountQueryString(queryString);
        return find(queryString, queryCount, params, pageable, transformer);
    }

    public Page<T> find(String queryString, String queryCount, Pageable pageable) {
        return find(queryString, queryCount, NamedParams.newParams(), pageable);
    }

    public <S> Page<S> find(String queryString, String queryCount, Pageable pageable, Class<S> transformere) {
        return find(queryString, queryCount, NamedParams.newParams(), pageable, transformere);
    }

    public <S> Page<S> find(String queryString, String queryCount, Pageable pageable, ResultTransformer transformer) {
        return find(queryString, queryCount, NamedParams.newParams(), pageable, transformer);
    }

    public Page<T> find(StringQuery queryString, StringQuery queryCount, Pageable pageable) {
        Assert.notNull(queryString, "StringQuery must not be null!");
        Assert.notNull(queryCount, "StringQuery count must not be null!");

        String query = queryString.getQuery();
        String count = queryCount.getQuery();
        NamedParams params = queryString.getParams();
        return find(query, count, params, pageable);
    }

    public <S> Page<S> find(StringQuery queryString, StringQuery queryCount, Pageable pageable, Class<S> transformer) {
        Assert.notNull(queryString, "StringQuery must not be null!");
        Assert.notNull(queryCount, "StringQuery count must not be null!");
        Assert.notNull(transformer, "TransformerClass must not be null!");

        String query = queryString.getQuery();
        String count = queryCount.getQuery();
        NamedParams params = queryString.getParams();
        return find(query, count, params, pageable, transformer);
    }

    public <S> Page<S> find(StringQuery queryString, StringQuery queryCount, Pageable pageable, ResultTransformer transformer) {
        Assert.notNull(queryString, "StringQuery must not be null!");
        Assert.notNull(queryCount, "StringQuery count must not be null!");
        Assert.notNull(transformer, "Transformer must not be null!");

        String query = queryString.getQuery();
        String count = queryCount.getQuery();
        NamedParams params = queryString.getParams();
        return find(query, count, params, pageable, transformer);
    }

    public Page<T> find(String queryString, String queryCount, NamedParams params, Pageable pageable) {
        return getPage(pageable, queryString, params, queryCount);
    }

    public <S> Page<S> find(String queryString, String queryCount, NamedParams params, Pageable pageable, Class<S> transformer) {
        Assert.hasText(queryString, "Query must has text!");
        Assert.hasText(queryCount, "Query count must has text!");
        Assert.notNull(params, "QueryParams must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");
        Assert.notNull(transformer, "TransformerClass must not be null!");

        Query query = em.createQuery(queryString);

        setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(Integer.valueOf(pageable.getOffset() + ""));

        List<S> resultList = query.unwrap(QueryImpl.class).setResultTransformer(Transformers.aliasToBean(transformer)).list();

        Query countQuery = em.createQuery(queryCount);
        setQueryParams(countQuery, params);
        Long total = (Long) countQuery.getSingleResult();

        Page<S> page = new PageImpl(resultList, pageable, total);

        return page;
    }

    public <S> Page<S> find(String queryString, String queryCount, NamedParams params, Pageable pageable, ResultTransformer transformer) {
        Assert.hasText(queryString, "Query must has text!");
        Assert.hasText(queryCount, "Query count must has text!");
        Assert.notNull(params, "QueryParams must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");
        Assert.notNull(transformer, "Transformer must not be null!");

        Query query = em.createQuery(queryString);

        setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(Integer.valueOf(pageable.getOffset() + ""));

        List<S> resultList = query.unwrap(QueryImpl.class).setResultTransformer(transformer).list();

        Query countQuery = em.createQuery(queryCount);
        setQueryParams(countQuery, params);
        Long total = (Long) countQuery.getSingleResult();

        Page<S> page = new PageImpl(resultList, pageable, total);

        return page;
    }

    public void executeUpdate(String jpql) {
        executeUpdate(jpql, NamedParams.newParams());
    }

    public void executeUpdate(StringQuery stringQuery) {
        Assert.notNull(stringQuery, "StringQuery myst not be null");
        executeUpdate(stringQuery.getQuery(), stringQuery.getParams());
    }

    public void executeUpdate(String jpql, NamedParams params) {
        Assert.hasText(jpql, "JPQL must has text!");
        Assert.notNull(params, "Query Params must not be null!");

        Query query = em.createQuery(jpql);
        setQueryParams(query, params);
        query.executeUpdate();
    }


    public List<T> findBySql(String sql) {
        return findBySql(sql, NamedParams.newParams());
    }

    public List findObjectBySql(StringQuery stringQuery) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();
        return findBySql(query, params);
    }

    public <S> List<S> findBySql(StringQuery stringQuery, Class<S> transformer) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String sql = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();

        Assert.notNull(params, "NamedParams must not be null!");

        Query query = em.createNativeQuery(sql);
        setQueryParams(query, params);

        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        stringQuery.getScalars().forEach((s, type) -> {
            sqlQuery.addScalar(s, type);
        });

        return sqlQuery.setResultTransformer(Transformers.aliasToBean(transformer)).list();
    }


    public List<T> findBySql(StringQuery stringQuery) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();
        return findBySql(query, params);
    }

    public List findObjectBySql(String sql, NamedParams params) {
        Assert.notNull(sql, "Query must not be null!");
        Assert.notNull(params, "NamedParams must not be null!");

        Query query = em.createNativeQuery(sql);
        setQueryParams(query, params);
        return query.getResultList();
    }

    public Page findObjectBySql(StringQuery stringQuery, Pageable pageable) {
        String sql = stringQuery.getQuery();
        Assert.hasText(sql, "Sql must has text!");
        Query query = em.createNativeQuery(sql);
        NamedParams params = stringQuery.getParams();
        setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(Integer.valueOf(pageable.getOffset() + ""));

        List resultList = query.getResultList();
        String countSql = QueryUtils.genCountQueryString(sql);

        Query countQuery = em.createNativeQuery(countSql);
        setQueryParams(countQuery, params);
        Long total = Long.valueOf(countQuery.getSingleResult().toString());

        Page page = new PageImpl(resultList, pageable, total);
        return page;
    }

    public List findBySql(StringQuery stringQuery, ResultTransformer transformer) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String sql = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();

        Assert.notNull(params, "NamedParams must not be null!");

        Query query = em.createNativeQuery(sql);
        setQueryParams(query, params);

        NativeQuery sqlQuery = query.unwrap(NativeQuery.class);
        stringQuery.getScalars().forEach((s, type) -> {
            sqlQuery.addScalar(s, type);
        });

        return sqlQuery.setResultTransformer(transformer).list();
    }

    public Page findBySql(StringQuery stringQuery, Pageable pageable, ResultTransformer transformer) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");
        Assert.notNull(transformer, "Transformer must not be null!");
        String sql = stringQuery.getQuery();
        Assert.hasText(sql, "Sql must has text!");
        Query query = em.createNativeQuery(sql);
        NamedParams params = stringQuery.getParams();
        setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(Integer.valueOf(pageable.getOffset() + ""));

        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        stringQuery.getScalars().forEach((s, type) -> {
            sqlQuery.addScalar(s, type);
        });

        List resultList = sqlQuery.setResultTransformer(transformer).list();
        String countSql = QueryUtils.genCountQueryString(sql);

        Query countQuery = em.createNativeQuery(countSql);
        setQueryParams(countQuery, params);
        Long total = Long.valueOf(countQuery.getSingleResult().toString());

        Page page = new PageImpl(resultList, pageable, total);
        return page;
    }

    public <S> Page<S> findBySql(StringQuery stringQuery, Pageable pageable, Class<S> transformer) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");
        Assert.notNull(transformer, "TransformerClass must not be null!");
        String sql = stringQuery.getQuery();
        Assert.hasText(sql, "Sql must has text!");
        Query query = em.createNativeQuery(sql);
        NamedParams params = stringQuery.getParams();
        setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(Integer.valueOf(pageable.getOffset() + ""));

        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        stringQuery.getScalars().forEach((s, type) -> {
            sqlQuery.addScalar(s, type);
        });

        List<S> resultList = sqlQuery.setResultTransformer(Transformers.aliasToBean(transformer)).list();
        String countSql = QueryUtils.genCountQueryString(sql);

        Query countQuery = em.createNativeQuery(countSql);
        setQueryParams(countQuery, params);
        Long total = Long.valueOf(countQuery.getSingleResult().toString());

        Page page = new PageImpl(resultList, pageable, total);
        return page;
    }

    public EntityManager getEm() {
        return em;
    }

    public List<T> findBySql(String sql, NamedParams params) {
        Assert.notNull(sql, "Query must not be null!");
        Assert.notNull(params, "NamedParams must not be null!");

        Query query = em.createNativeQuery(sql);
        setQueryParams(query, params);
        return query.getResultList();
    }

    public Page<T> findBySql(String sql, Pageable pageable) {
        return findBySql(sql, NamedParams.newParams(), pageable);
    }

    public Page<T> findBySql(StringQuery stringQuery, Pageable pageable) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();
        return findBySql(query, params, pageable);
    }

    public Page<T> findBySql(String sql, NamedParams params, Pageable pageable) {
        Assert.hasText(sql, "Sql must has text!");

        String queryCount = QueryUtils.genCountQueryString(sql);
        return findBySql(sql, queryCount, params, pageable);
    }

    public Page<T> findBySql(String sql, String countSql, Pageable pageable) {
        return findBySql(sql, countSql, NamedParams.newParams(), pageable);
    }

    public Page<T> findBySql(StringQuery queryString, StringQuery queryCount, Pageable pageable) {
        Assert.notNull(queryString, "StringQuery must not be null!");
        Assert.notNull(queryCount, "StringQuery count must not be null!");

        String query = queryString.getQuery();
        String count = queryCount.getQuery();
        NamedParams params = queryString.getParams();
        return findBySql(query, count, params, pageable);
    }

    public Page<T> findBySql(String sql, String countSql, NamedParams params, Pageable pageable) {
        Assert.hasText(sql, "Query must has text!");
        Assert.hasText(countSql, "Count sql must has text!");
        Assert.notNull(params, "QueryParams must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");

        Query query = em.createNativeQuery(sql);

        setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(Integer.valueOf(pageable.getOffset() + ""));

        List<T> resultList = query.getResultList();

        Query countQuery = em.createNativeQuery(countSql);
        setQueryParams(countQuery, params);
        Long total = Long.valueOf(countQuery.getSingleResult().toString());

        Page<T> page = new PageImpl(resultList, pageable, total);
        return page;
    }

    public void executeUpdateBySql(String sql) {
        executeUpdateBySql(sql, NamedParams.newParams());
    }

    public void executeUpdateBySql(StringQuery stringQuery) {
        Assert.notNull(stringQuery, "StringQuery must not be null!");

        String query = stringQuery.getQuery();
        NamedParams params = stringQuery.getParams();

        executeUpdateBySql(query, params);
    }

    public void executeUpdateBySql(String sql, NamedParams params) {
        Assert.notNull(params, "Query params must not be null!");
        Assert.hasText(sql, "Sql must has text!");

        Query query = em.createNativeQuery(sql);
        setQueryParams(query, params);
        query.executeUpdate();
    }

}
