package com.ueboot.shiro.repository.permission.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限查询返回结果类，减少多表关联查询时出现性能问题
 *
 * @author yangkui
 */
@Getter
@Setter
public class PermissionBo {

    private Long resourceId;
    private String resourceName;
    private String resourceType;
    private String url;
    private String themeJson;
    private String permission;
    private String parentName;
    private Long parentId;
    private Long rank = 0L;
}
