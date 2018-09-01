/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:StoredProcedureJpaResository.java   2018-08-29 17:35 wanglijun
 */
package com.ueboot.core.jpa.repository;

import com.ueboot.core.jpa.repository.query.NamedParams;
import com.ueboot.core.jpa.repository.query.StringQuery;
import org.springframework.util.CollectionUtils;

import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: 存储过程调用
 * </p>
 * <p>
 * Description:存储过程调用
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: XiQiao
 * </p>
 *
 * @author: wanglijun
 * @version：1.0
 */
public class StoredProcedureJpaRepositoryImpl<T, ID extends Serializable>   extends DefaultJpaRepository implements  StoredProcedureRepository{

    /***
     * 执行存储过程
     * @param stringQuery 查询对象
     * @return Boolean 执行是否成功
     */
    @Override
    public Boolean executeStoredProcedure(StringQuery stringQuery){
        StoredProcedureQuery query=this.em.createNamedStoredProcedureQuery (stringQuery.getQuery ());
        this.setParameter (query,stringQuery.getParams ());
        return query.execute ();
    }

    /***
     *
     * @param stringQuery stringQuery
     * @return List<T> 结果集
     */
    @Override
    public List<T> executeResultList(StringQuery stringQuery) {
        StoredProcedureQuery query=this.em.createNamedStoredProcedureQuery (stringQuery.getQuery ());
        this.setParameter (query,stringQuery.getParams ());
        query.execute ();
        List list=query.getResultList ();
        return list;
    }


    /***
     * 设置参数
     * @param storedProcedureQuery 存储
     * @param namedParams 对象
     */
    private void setParameter(StoredProcedureQuery storedProcedureQuery, NamedParams namedParams){
        Map<String,Object> params=namedParams.getParameters ();
        if(CollectionUtils.isEmpty (params)){
            return;
        }
        for(Map.Entry<String,Object> entry: params.entrySet ()){
            storedProcedureQuery.setParameter (entry.getKey (),entry.getValue ());
        }
    }
}
