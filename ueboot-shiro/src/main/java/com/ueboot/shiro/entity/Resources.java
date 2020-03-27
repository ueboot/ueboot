package com.ueboot.shiro.entity;

import com.ueboot.core.entity.AbstractVersionEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 菜单和按钮资源，用于控制菜单显示的位置、给予角色相关的权限。
 *
 * @author Neel
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "PROPERTY_SYS_RESOURCES")
public class Resources extends AbstractVersionEntity<Long> {

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


    /**
     * 资源名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 资源类型，[menu|button|other]
     */
    @Column(name = "RESOURCE_TYPE")
    private String resourceType;

    /**
     * 资源路径
     */
    @Column(name = "URL")
    private String url;

    /**
     * 界面渲染描述JSON
     */
    @Column(name = "THEME_JSON")
    private String themeJson;

    /**
     * 权限描述 （权限字符串,menu例子：chRole:*，button例子：chRole:create,chRole:update,chRole:delete,chRole:view）
     */
    @Column(name = "PERMISSION")
    private String permission;
    /**
     * 父级资源
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Resources parent;

    /**
     * 父级资源名称
     */
    @Column(name = "PARENT_NAME")
    private String parentName;

    /**
     * 排序
     */
    @Column(name = "RANK_")
    private Long rank = 0L;

    /**
     * 是否启用
     */
    @Column(name = "AVAILABLE")
    private Boolean available = Boolean.FALSE;

}
