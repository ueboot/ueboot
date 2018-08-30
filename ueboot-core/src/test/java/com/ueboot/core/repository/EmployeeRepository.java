/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:EmployeeRepository.java   2018-08-29 19:03 wanglijun
 */
package com.ueboot.core.repository;

import com.ueboot.core.EmployeeEntity;
import org.springframework.stereotype.Repository;

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
 * @create: 2018-08-29 19:03
 * @version：1.0
 */
@Repository
public interface EmployeeRepository  extends BaseRepository<EmployeeEntity,Long>,EmployeeBaseRepository{

}
