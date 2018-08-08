package com.ueboot.shiro.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 菜单和按钮资源，用于控制菜单显示的位置、给予角色相关的权限。
 * @author Neel
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "SYS_RESOURCES")
public class Resources implements Serializable {

    /**
     * 资源类型：菜单组（仅仅是一个组的名称，没有后台接口功能）
     */
    public static final String RESOURCE_TYPE_GROUP = "菜单组";
    public static final String RESOURCE_TYPE_MENU = "菜单";
    public static final String RESOURCE_TYPE_BUTTON = "功能";
    public static final String RESOURCE_TYPE_OTHER = "其他";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 代码 */
    @Column(name = "CODE")
    private String code;

    /** 资源名称 */
    @Column(name = "NAME")
    private String name;

    /** 资源类型，[menu|button|other] */
    @Column(name = "RESOURCE_TYPE", columnDefinition="enum('菜单组', '菜单','功能', '其他')")
    private String resourceType;

    /** 资源路径 */
    @Column(name = "URL")
    private String url;

    /** 界面渲染描述JSON */
    @Column(name = "THEME_JSON")
    private String themeJson;

    /** 权限描述 （权限字符串,menu例子：chRole:*，button例子：chRole:create,chRole:update,chRole:delete,chRole:view） */
    @Column(name = "PERMISSION")
    private String permission;

    /** 父CODE */
    @Column(name = "PARENT_CODE")
    private String parentCode;

    /** 父CODE集合 如：1-10- */
    @Column(name = "PARENT_PATH")
    private String parentPath;

    /** 排序 */
    @Column(name = "RANK_")
    private Long rank = 0L;

    /** 层级 */
    @Column(name = "LEVEL")
    private Long level = 0L;

    /** 是否启用 */
    @Column(name = "AVAILABLE")
    private Boolean available = Boolean.FALSE;

}
