/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:EmployeeService.java   2018-08-29 19:19 wanglijun
 */
package com.ueboot.core.service;

import com.ueboot.core.EmployeeEntity;
import com.ueboot.core.jpa.repository.query.StringQuery;

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
public interface EmployeeService {

    Boolean  execute(StringQuery stringQuery);

    List<EmployeeEntity> executeResultList(StringQuery stringQuery);
}
