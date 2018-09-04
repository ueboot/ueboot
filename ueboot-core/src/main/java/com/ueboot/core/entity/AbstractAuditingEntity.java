/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:AbstractAuditingEntity.java   2018-06-30 15:12 wanglijun
 */
package com.ueboot.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * Title: AbstractAuditingEntity
 * </p>
 * <p>
 * Description: AbstractAuditingEntity(创建人，创建时间，更新人，更新时间)
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
@MappedSuperclass
@EntityListeners (AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<PK>  extends AbstractSuperEntity<PK> {

    /** 创建人名称 */
    @CreatedBy
    @Column(name = "CREATED_BY")
    protected String createdBy;

    /** 首次插入时间 */
    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal (TemporalType.TIMESTAMP)
    protected Date createdDate;

    /** 最后修改人名称 */
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    protected String lastModifiedBy;

    /** 最后修改时间 */
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal (TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;
}
