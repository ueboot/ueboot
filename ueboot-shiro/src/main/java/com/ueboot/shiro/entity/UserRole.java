package com.ueboot.shiro.entity;

import com.ueboot.core.entity.AbstractVersionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * 用户所属角色
 * @author Neel
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "T_SYS_USER_ROLE")
public class UserRole extends AbstractVersionEntity<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ROLE_ID")
    private Role role;

}
