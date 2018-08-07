package com.ueboot.shiro.entity;


import com.ueboot.core.entity.AbstractVersionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Neel
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "SYS_ROLE")
public class Role extends AbstractVersionEntity<Long> {

    /** 超级管理员*/
    public static final Long ADMIN_VALUE = 1L;
    /** 普通管理员*/
    public static final Long MANAGER_VALUE = 2L;
    /** 普通用户*/
    public static final Long OPERATOR_VALUE = 3L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** CODE */
    @Column(name = "CODE")
    private String code;

    /** 机构CODE */
    @Column(name = "ORG_CODE")
    private String orgCode;

    /** 角色标识程序中判断使用,如"admin",这个是唯一的 */
    @Column(name = "ROLE")
    private String role;

    /** 角色名称 */
    @Column(name = "NAME")
    private String name;

    /** 角色描述 */
    @Column(name = "DESCRIPTION")
    private String description;

    /** 可用 */
    @Column(name = "AVAILABLE")
    private Boolean available = Boolean.FALSE;

}
