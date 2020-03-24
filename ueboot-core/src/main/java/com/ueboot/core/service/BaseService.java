/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * AbstractService.java 2017-10-09 上午8:55
 */

package com.ueboot.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 描述:基础服务类，其他service继承该类即可获取常用服务功能
 *
 * @author yangkui create on 2017-10-09.
 * @since 1.0
 */
public interface BaseService<T> {

    List<T> findAll();

    Page<T> findBy(Pageable pageable);

    T save(T object);

    T get(Long id);

    void delete(Long[] ids);
    void delete(Long id);
    void delete(T object);
}
