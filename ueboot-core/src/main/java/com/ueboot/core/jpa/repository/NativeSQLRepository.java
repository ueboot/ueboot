package com.ueboot.core.jpa.repository;


import com.ueboot.core.jpa.repository.query.NamedParams;
import com.ueboot.core.jpa.repository.query.StringQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * 加入符合 JpaTemplate 习惯方法
 * @author xiangli.ma
 * @date 2018/7/21
 * @since 1.0
 */
public interface NativeSQLRepository<T, ID extends Serializable> extends Repository {

    long countBySql(StringQuery stringQuery);

    /**
     * Query
     * @param sql
     * @return
     */
    List<T> findBySql(String sql);

    /**
     * Query
     * @param stringQuery
     * @return
     */
    List<T> findBySql(StringQuery stringQuery);

    /**
     * Query
     * @param sql
     * @param params
     * @return
     */
    List<T> findBySql(String sql, NamedParams params);

    /**
     * Query
     * @param sql
     * @param pageable
     * @return
     */
    Page<T> findBySql(String sql, Pageable pageable);

    /**
     * Query
     * @param stringQuery
     * @param pageable
     * @return
     */
    Page<T> findBySql(StringQuery stringQuery, Pageable pageable);

    /**
     * Query
     * @param sql
     * @param params
     * @param pageable
     * @return
     */
    Page<T> findBySql(String sql, NamedParams params, Pageable pageable);

    /**
     * Query
     * @param sql
     * @param countSql
     * @param pageable
     * @return
     */
    Page<T> findBySql(String sql, String countSql, Pageable pageable);

    /**
     * Query
     * @param queryString
     * @param queryCount
     * @param pageable
     * @return
     */
    Page<T> findBySql(StringQuery queryString, StringQuery queryCount, Pageable pageable);

    /**
     * Query
     * @param sql
     * @param countSql
     * @param params
     * @param pageable
     * @return
     */
    Page<T> findBySql(String sql, String countSql, NamedParams params, Pageable pageable);

    /**
     * Query
     * @param sql
     */
    void executeUpdateBySql(String sql);

    /**
     * Query
     * @param stringQuery
     */
    void executeUpdateBySql(StringQuery stringQuery);

    /**
     * Query
     * @param sql
     * @param params
     */
    void executeUpdateBySql(String sql, NamedParams params);

}
