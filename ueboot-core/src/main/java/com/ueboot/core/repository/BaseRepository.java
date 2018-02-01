package com.ueboot.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * 基础的repository类
 * @author yangkui
 * @param <T> Entity实体类名称
 * @param <ID> Entity 唯一主键
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID>, JpaRepository<T, ID>,PagingAndSortingRepository<T, ID> ,JpaSpecificationExecutor<T> {

}
