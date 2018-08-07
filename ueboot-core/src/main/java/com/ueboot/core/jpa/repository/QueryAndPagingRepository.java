package com.ueboot.core.jpa.repository;

import com.ueboot.core.jpa.repository.query.NamedParams;
import com.ueboot.core.jpa.repository.query.StringQuery;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author xiangli.ma
 * @date 2018/7/21
 * @since 1.0
 */
public interface QueryAndPagingRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    /**
     * Query
     * @param queryString
     * @return
     */
    List<T> find(String queryString);

    /**
     * Query
     * @param stringQuery
     * @return
     */
    List<T> find(StringQuery stringQuery);

    /**
     * Query
     * @param queryString
     * @param params
     * @return
     */
    List<T> find(String queryString, NamedParams params);

    /**
     * Query
     * @param queryString
     * @param pageable
     * @return
     */
    Page<T> find(String queryString, Pageable pageable);

    /**
     * Query
     * @param stringQuery
     * @param pageable
     * @return
     */
    Page<T> find(StringQuery stringQuery, Pageable pageable);

    <S> Page<S> find(StringQuery stringQuery, Pageable pageable, Class<S> transformerClass);

    <S> Page<S> find(StringQuery stringQuery, Pageable pageable, ResultTransformer transformer);

    /**
     * Query
     * @param queryString
     * @param params
     * @param pageable
     * @return
     */
    Page<T> find(String queryString, NamedParams params, Pageable pageable);

    <S> Page<S> find(String queryString, NamedParams params, Pageable pageable, Class<S> transformerClasse);

    <S> Page<S> find(String queryString, NamedParams params, Pageable pageable, ResultTransformer transformer);

    /**
     * Query
     * @param queryString
     * @param queryCount
     * @param pageable
     * @return
     */
    Page<T> find(String queryString, String queryCount, Pageable pageable);

    <S> Page<S> find(String queryString, String queryCount, Pageable pageable, Class<S> transformerClasse);

    <S> Page<S> find(String queryString, String queryCount, Pageable pageable, ResultTransformer transformer);

    /**
     * Query
     * @param queryString
     * @param queryCount
     * @param pageable
     * @return
     */
    Page<T> find(StringQuery queryString, StringQuery queryCount, Pageable pageable);

    <S> Page<S> find(StringQuery queryString, StringQuery queryCount, Pageable pageable, Class<S> transformerClass);

    <S> Page<S> find(StringQuery queryString, StringQuery queryCount, Pageable pageable, ResultTransformer transformer);

    /**
     * Query
     * @param queryString
     * @param queryCount
     * @param params
     * @param pageable
     * @return
     */
    Page<T> find(String queryString, String queryCount, NamedParams params, Pageable pageable);

    <S> Page<S> find(String queryString, String queryCount, NamedParams params, Pageable pageable, Class<S> transformerClass);

    <S> Page<S> find(String queryString, String queryCount, NamedParams params, Pageable pageable, ResultTransformer transformer);
}
