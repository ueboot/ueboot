/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:EmployeeRepository.java   2018-08-29 19:04 wanglijun
 */
package com.ueboot.core.repository.impl;

import com.ueboot.core.EmployeeEntity;
import com.ueboot.core.jpa.repository.StoredProcedureJpaRepositoryImpl;
import com.ueboot.core.jpa.repository.query.StringQuery;
import com.ueboot.core.repository.EmployeeBaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: XiQiao
 * </p>
 *
 * @author: wanglijun
 * @create: 2018-08-29 19:04
 * @version：1.0
 */
@Slf4j
@Repository
public class EmployeeRepositoryImpl extends StoredProcedureJpaRepositoryImpl<EmployeeEntity,Long> implements EmployeeBaseRepository {

    @Override
    public Boolean execute(StringQuery stringQuery) {
        return this.executeStoredProcedure (stringQuery);
    }


    public List<EmployeeEntity> executeList(StringQuery stringQuery){
        return super.executeResultList (stringQuery);
    }
}
