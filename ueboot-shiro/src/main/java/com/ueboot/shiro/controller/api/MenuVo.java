package com.ueboot.shiro.controller.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MenuVo {

    private Long id;

    /** 资源名称 */
    private String name;

    /** 资源类型，[menu|button|other] */
    private String resourceType;

    /** 资源路径 */
    private String url;

    /** 界面渲染描述JSON */
    private Map themeJson;

    /** 权限描述 */
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view

    /** 父ID */
    private Long parentId; //父编号

    /** 是否启用 */
    private Boolean available = Boolean.FALSE;
    /**排序*/
    private Long rank = 0L;

}
