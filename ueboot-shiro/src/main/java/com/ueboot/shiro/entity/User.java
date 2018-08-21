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
@Table(name = "SYS_USER")
public class User extends AbstractVersionEntity<Long> {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 机构CODE */
    @Column(name = "ORG_CODE")
    private String orgCode;

    /** 用户名 */
    @NotEmpty(message = "用户名不能为空")
    @Column(name = "USERNAME")
    private String userName;

    /** 密码 */
    @NotEmpty(message = "密码不能为空")
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 是否被锁，被锁后，无法登陆
     */
    @Column(name = "IS_LOCKED")
    private boolean locked;

    /**
     * 密码过期日期，如果为空，则永远不过期
     */
    @Column(name = "CREDENTIAL_EXPIRED_DATE")
    private Date credentialExpiredDate;

}
