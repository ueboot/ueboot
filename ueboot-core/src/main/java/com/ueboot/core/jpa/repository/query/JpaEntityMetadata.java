package com.ueboot.core.jpa.repository.query;


import org.springframework.data.repository.core.EntityMetadata;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by xiangli.ma on 2015/8/5.
 */
public interface JpaEntityMetadata<T> extends EntityMetadata<T> {

    String getEntityName();

    /**
     * Returns the id attribute of the entity.
     *
     * @return
     */
    SingularAttribute<? super T, ?> getIdAttribute();

    /**
     * Returns {@literal true} if the entity has a composite id.
     *
     * @return
     */
    boolean hasCompositeId();

    /**
     * Returns the attribute names of the id attributes. If the entity has a composite id, then all id attribute names are
     * returned. If the entity has a single id attribute then this single attribute name is returned.
     *
     * @return
     */
    Iterable<String> getIdAttributeNames();

}
