/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:EmployeeRepository.java   2018-08-29 19:03 wanglijun
 */
package com.ueboot.core.repository;

import com.ueboot.core.jpa.repository.StoredProcedureRepository;
import com.ueboot.core.jpa.repository.query.StringQuery;
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
 * @create: 2018-08-29 19:03
 * @version：1.0
 */
@Repository
public interface EmployeeBaseRepository<EmployeeEntity,Long>  extends StoredProcedureRepository {
    /***
     *
     * @param stringQuery
     * @return
     */
    Boolean  execute(StringQuery stringQuery);

    /***
     *
     * @param stringQuery
     * @return
     */
    List<EmployeeEntity> executeList(StringQuery stringQuery);
}
