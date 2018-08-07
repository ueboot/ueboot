package com.ueboot.core.jpa.repository.query;


import org.springframework.data.repository.core.EntityMetadata;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by xiangli.ma on 2015/8/5.
 */
public interface JpaEntityMetadata<T> extends EntityMetadata<T> {

    String getEntityName();


    SingularAttribute<? super T, ?> getIdAttribute();


    boolean hasCompositeId();

    Iterable<String> getIdAttributeNames();

}
