package com.ueboot.shiro.entity;


import com.ueboot.core.entity.AbstractVersionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户角色
 * @author yangkui
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "PROPERTY_SYS_ROLE")
public class Role extends AbstractVersionEntity<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 角色名称 */
    @Column(name = "NAME")
    private String name;

    /** 角色描述 */
    @Column(name = "DESCRIPTION")
    private String description;

    /** 可用 */
    @Column(name = "AVAILABLE")
    private Boolean available = Boolean.TRUE;

}
