package com.ueboot.shiro.entity;

import com.ueboot.core.entity.AbstractVersionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zl  on 2017-11-10 15:39:14.
 * - 用户实体类
 * @author zl
 */

@Setter
@Getter
@NoArgsConstructor
@Entity

@Table(name = "PROPERTY_SYS_USER")
public class User extends AbstractVersionEntity<Long> {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 机构CODE */
    @Column(name = "ORG_CODE")
    private String orgCode;

    /** 用户名 */
    @Column(name = "USERNAME")
    private String userName;

    /** 密码 */
    @Column(name = "PASSWORD")
    private String password;

    /**用户所属的角色名称，用于页面查看使用。不做权限配置*/
    @Column(name = "ROLE_NAMES")
    private String roleNames;

    /**用户所属的角色id，用于快速获取用户角色，分配角色时会进行更新*/
    @Column(name="ROLE_IDS")
    private String roleIds;

    /**
     * 是否被锁，被锁后，无法登陆
     */
    @Column(name = "IS_LOCKED")
    private boolean locked;

    /**
     * 是否有效，无效后无法登录与锁定效果类似
     */
    @Column(name = "valid")
    private boolean valid = Boolean.TRUE;

    /**
     * 密码过期日期，如果为空，则永远不过期
     */
    @Column(name = "CREDENTIAL_EXPIRED_DATE")
    private Date credentialExpiredDate;

}
