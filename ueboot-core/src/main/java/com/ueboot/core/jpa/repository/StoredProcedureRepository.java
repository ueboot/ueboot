/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:StoredProcedureJpaRepository.java   2018-08-29 19:12 wanglijun
 */
package com.ueboot.core.jpa.repository;

import com.ueboot.core.jpa.repository.query.StringQuery;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: 存储过程调用
 * </p>
 * <p>
 * Description: 存储过程调用
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: XiQiao
 * </p>
 *
 * @author: wanglijun
 * @create: 2018-08-29 19:12
 * @version：1.0
 */
public interface StoredProcedureRepository<T, ID extends Serializable> {
    /***
     * 执行存储过程调用
     * @param stringQuery 查询语句
     * @return Boolean
     */
    Boolean executeStoredProcedure(StringQuery stringQuery);

    /***
     *
     * @param stringQuery
     * @return  List<T>
     */
    List<T> executeResultList(StringQuery stringQuery);
}
