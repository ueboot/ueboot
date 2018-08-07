/*
 * Copyright (c)  2018, XiQiao
 * All rights reserved. 
 *
 * Id:Entity.java   2018-06-30 15:02 wanglijun
 */
package com.ueboot.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * <p>
 * Title: 实体基本类
 * </p>
 * <p>
 * Description: 实体基本类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: XiQiao
 * </p>
 *
 * @author:  wanglijun
 * @create: 2018-06-30 15:02
 * @version：1.0
 */

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractSuperEntity<PK> implements Serializable{

    /**序列*/
    private static final long serialVersionUID = 3056556925122030761L;
    /**主键名称*/
    public static final String ID="id";

    /***
     *  获取用户的主键
     * @return PK
     */
    public  abstract PK getId();


    /*
	 * <p>Title: hashCode</p> <p>Description: </p>
	 *
	 * @return
	 *
	 * @see java.lang.Object#hashCode()
	 */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getId() == null) ? super.hashCode() : getId().hashCode());
        return result;
    }

	/*
	 * <p>Title: equals</p> <p>Description: </p>
	 *
	 * @param obj*
	 *
	 * @return boolean
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (super.getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        AbstractSuperEntity<PK> other = (AbstractSuperEntity<PK>) obj;
        if (getId() == null){
            return true;
        }
        if (other.getId() != null) {
            return false;
        }else if(!(getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

}
