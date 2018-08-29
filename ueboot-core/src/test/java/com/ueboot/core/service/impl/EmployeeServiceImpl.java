/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:EmployeeServiceImpl.java   2018-08-29 19:19 wanglijun
 */
package com.ueboot.core.service.impl;

import com.ueboot.core.EmployeeEntity;
import com.ueboot.core.exception.BusinessException;
import com.ueboot.core.jpa.repository.query.StringQuery;
import com.ueboot.core.repository.EmployeeRepository;
import com.ueboot.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
 * @create: 2018-08-29 19:19
 * @version：1.0
 */
@Service
@Transactional(readOnly =true, rollbackFor = BusinessException.class)
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository repository;

    @Override
    @Transactional(timeout = 30,readOnly =true, rollbackFor = BusinessException.class,propagation = Propagation.REQUIRED)
    public Boolean execute(StringQuery stringQuery) {
        return repository.execute (stringQuery);
    }

    @Override
    public List<EmployeeEntity> executeResultList(StringQuery stringQuery) {
        return repository.executeList (stringQuery);
    }
}
