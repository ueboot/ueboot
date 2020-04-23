package com.ueboot.shiro.entity;


import com.ueboot.core.entity.AbstractVersionEntity;
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
public class Permission extends AbstractVersionEntity<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="RESOURCE_ID")
    private Resources resource;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ROLE_ID")
    private Role role;

}
