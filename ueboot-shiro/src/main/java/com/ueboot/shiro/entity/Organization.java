package com.ueboot.shiro.entity;

import com.ueboot.core.entity.AbstractVersionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * 管理人员所属的机构,机构可以有子机构。
 * @author yangkui
 * @author Neel
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "PROPERTY_SYS_ORGANIZATION")
public class Organization extends AbstractVersionEntity<Long> {


    public static final String RESOURCE_TYPE_ORGANIZATION = "机构";
    public static final String RESOURCE_TYPE_DEPARTMENT = "部门";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 机构代码，保证代码唯一 */
    @Column(name = "CODE")
    private String code;

    /** 机构名称 */
    @Column(name = "NAME")
    private String name;

    /** 机构地址 */
    @Column(name = "ADDRESS")
    private String address;

    /** 机构电话 */
    @Column(name = "TELEPHONE")
    private String telephone;

    /** 机构类型，[机构|部门|other] */
    @Column(name = "TYPE")
    private String type;

    /** 父CODE */
    @Column(name = "PARENT_CODE")
    private String parentCode;

    /** 父ID集合 如：1-10- */
    @Column(name = "PARENT_PATH")
    private String parentPath;

    /** 层级 */
    @Column(name = "LEVEL")
    private Long level = 0L;

    /** 是否启用 */
    @Column(name = "AVAILABLE")
    private Boolean available = Boolean.FALSE;

}
