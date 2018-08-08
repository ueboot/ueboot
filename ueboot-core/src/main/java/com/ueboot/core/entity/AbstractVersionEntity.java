/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved.
 *
 * Id:AbstractVersionEntity.java   2018-06-30 15:21 wanglijun
 */
package com.ueboot.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Version;

/**
 * <p>
 * Title: AbstractVersionEntity
 * </p>
 * <p>
 * Description: AbstractVersionEntity (逻辑删除标识,乐观锁)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: XiQiao
 * </p>
 *
 * @author: wanglijun
 * @version：1.0
 */

@Getter
@Setter
public abstract class AbstractVersionEntity<PK>  extends AbstractAuditingEntity<PK> {

    /**乐观锁*/
    @Version
    @Column(name="OPT_VERSION")
    protected  Long version;
    /**逻辑是否删除*/
    @Column(name="DELETED")
    protected  Boolean deleted=Boolean.FALSE;

}
