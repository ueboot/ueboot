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
 * @since 1.0
 */
public interface NativeSQLRepository<T, ID extends Serializable> extends Repository {

    long countBySql(StringQuery stringQuery);


    List<T> findBySql(String sql);

    List<T> findBySql(StringQuery stringQuery);

    List<T> findBySql(String sql, NamedParams params);

    Page<T> findBySql(String sql, Pageable pageable);

    Page<T> findBySql(StringQuery stringQuery, Pageable pageable);


    Page<T> findBySql(String sql, NamedParams params, Pageable pageable);


    Page<T> findBySql(String sql, String countSql, Pageable pageable);

    Page<T> findBySql(StringQuery queryString, StringQuery queryCount, Pageable pageable);


    Page<T> findBySql(String sql, String countSql, NamedParams params, Pageable pageable);


    void executeUpdateBySql(String sql);


    void executeUpdateBySql(StringQuery stringQuery);

    void executeUpdateBySql(String sql, NamedParams params);

}
