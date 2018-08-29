/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:EmployeeEntity.java   2018-08-29 18:16 wanglijun
 */
package com.ueboot.core;

import com.ueboot.core.entity.AbstractSuperEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
 * @create: 2018-08-29 18:16
 * @version：1.0
 */
@Setter
@Getter
@Entity
public class EmployeeList extends AbstractSuperEntity<Long> {

    public static final String PROCEDURE_QUERY="employeeList";

    /**ID**/
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_TEST_EMPLOYEE")
    @SequenceGenerator(name="SEQ_TEST_EMPLOYEE", sequenceName="SEQ_TEST_EMPLOYEE")
    private Long id;

    @Column(name="EMAIL")
    private String email;

    @Column(name="NAME")

    private String name;
    @Column(name="DEPT_NAME")
    private String deptName;


}
