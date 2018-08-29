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

@NamedStoredProcedureQueries (
        {
            @NamedStoredProcedureQuery (name=EmployeeEntity.PROCEDURE_QUERY_LIST,
            procedureName ="PROC_EMPLOYEE_SELECT",
                    resultClasses = EmployeeEntity.class,
                    parameters = {
                        @StoredProcedureParameter (mode = ParameterMode.REF_CURSOR,name="cur_out", type =Void.class)
                    }
            ),
            @NamedStoredProcedureQuery (name=EmployeeEntity.PROCEDURE_QUERY_ADD,procedureName ="ADD_EMPLOYEE_PROCEDURE",resultClasses = {EmployeeEntity.class},
                    parameters = {
            @StoredProcedureParameter (name="email",type=String.class,mode = ParameterMode.IN),
            @StoredProcedureParameter (name="name",type=String.class,mode = ParameterMode.IN),
            @StoredProcedureParameter (name="deptName",type=String.class,mode = ParameterMode.IN),
        })
        }
)
@Entity
@Table(name="TEST_EMPLOYEE")
public class EmployeeEntity  extends AbstractSuperEntity<Long> {

    public static final String PROCEDURE_QUERY_ADD="AddEmployee";


    public static final String PROCEDURE_QUERY_LIST="employeeList";

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
