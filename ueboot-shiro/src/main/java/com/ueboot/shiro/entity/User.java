package com.ueboot.shiro.entity;

import com.ueboot.core.entity.AbstractVersionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

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
    private String username;

    /** 密码 */
    @NotEmpty(message = "密码不能为空")
    @Column(name = "PASSWORD")
    private String password;

}
