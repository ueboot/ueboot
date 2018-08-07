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
 * @since 1.0
 */
public interface QueryAndPagingRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {


    List<T> find(String queryString);


    List<T> find(StringQuery stringQuery);


    List<T> find(String queryString, NamedParams params);


    Page<T> find(String queryString, Pageable pageable);


    Page<T> find(StringQuery stringQuery, Pageable pageable);

    <S> Page<S> find(StringQuery stringQuery, Pageable pageable, Class<S> transformerClass);

    <S> Page<S> find(StringQuery stringQuery, Pageable pageable, ResultTransformer transformer);


    Page<T> find(String queryString, NamedParams params, Pageable pageable);

    <S> Page<S> find(String queryString, NamedParams params, Pageable pageable, Class<S> transformerClasse);

    <S> Page<S> find(String queryString, NamedParams params, Pageable pageable, ResultTransformer transformer);


    Page<T> find(String queryString, String queryCount, Pageable pageable);

    <S> Page<S> find(String queryString, String queryCount, Pageable pageable, Class<S> transformerClasse);

    <S> Page<S> find(String queryString, String queryCount, Pageable pageable, ResultTransformer transformer);


    Page<T> find(StringQuery queryString, StringQuery queryCount, Pageable pageable);

    <S> Page<S> find(StringQuery queryString, StringQuery queryCount, Pageable pageable, Class<S> transformerClass);

    <S> Page<S> find(StringQuery queryString, StringQuery queryCount, Pageable pageable, ResultTransformer transformer);


    Page<T> find(String queryString, String queryCount, NamedParams params, Pageable pageable);

    <S> Page<S> find(String queryString, String queryCount, NamedParams params, Pageable pageable, Class<S> transformerClass);

    <S> Page<S> find(String queryString, String queryCount, NamedParams params, Pageable pageable, ResultTransformer transformer);
}
