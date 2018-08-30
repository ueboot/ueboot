/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:PersonStoredProcedureTest.java   2018-08-29 18:12 wanglijun
 */
package com.ueboot.core;

import com.ueboot.core.jpa.repository.query.StringQuery;
import com.ueboot.core.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
 * @create: 2018-08-29 18:12
 * @version：1.0
 */

@SpringBootTest(classes = {CoreApplication.class})

@RunWith (SpringRunner.class)
@Slf4j
public class PersonStoredProcedureTest {

    @Autowired
    private EmployeeService service;

    @Test
    public void  test(){
        log.info ("1111111");
    }

    @Test
    public void  execute(){
        StringQuery query=StringQuery.newQuery ()
                .query (EmployeeEntity.PROCEDURE_QUERY_ADD)
                .predicate (true)
                .param ("name","wanglijun")
                .param ("email","eamil")
                .param ("deptName","西樵软件")
                .build ();
        service.execute (query);
    }

    @Test
    public void  executeResultList(){
        StringQuery query=StringQuery.newQuery ()
                .query (EmployeeEntity.PROCEDURE_QUERY_LIST)
                .predicate (true)
                .build ();
       List<EmployeeEntity> list=service.executeResultList (query);
       for(EmployeeEntity employeeList:list){
           log.info (employeeList.getDeptName ()+" "+employeeList.getName ()+"  "+employeeList.getEmail ());
       }
    }
}
