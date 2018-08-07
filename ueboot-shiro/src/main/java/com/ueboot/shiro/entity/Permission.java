package com.ueboot.shiro.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色与菜单资源之间的关系（设定用户的权限）
 * @author yangkui
 * @author Neel
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "SYS_PERMISSION")
public class Permission implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="RESOURCE_CODE", referencedColumnName = "CODE")
    private Resources resource;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ROLE_CODE", referencedColumnName = "CODE")
    private Role role;

}
