package com.ueboot.core.jpa.repository.query;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.metamodel.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author xiangli.ma
 * @since 1.0
 */
public class DefaultJpaEntityMetadata<T, ID extends Serializable> implements JpaEntityMetadata<T> {

    private final Class<T> domainType;

    private Metamodel metamodel;

    private String entityName;

    private IdMetadata<T> idMetadata;

    public DefaultJpaEntityMetadata(Class<T> domainType) {
        Assert.notNull(domainType, "Domain type must not be null!");

        this.domainType = domainType;
    }


    public DefaultJpaEntityMetadata(Class<T> domainType, Metamodel metamodel) {

        Assert.notNull(domainType, "Domain type must not be null!");
        Assert.notNull(metamodel, "Metamodel must not be null!");

        this.domainType = domainType;
        this.metamodel = metamodel;

        ManagedType<T> type = metamodel.managedType(domainType);
        if (type == null) {
            throw new IllegalArgumentException("The given domain class can not be found in the given Metamodel!");
        }

        this.entityName = type instanceof EntityType ? ((EntityType<?>) type).getName() : null;

        if (!(type instanceof IdentifiableType)) {
            throw new IllegalArgumentException("The given domain class does not contain an id attribute!");
        }

        this.idMetadata = new IdMetadata<T>((IdentifiableType<T>) type);
    }


    @Override
    public Class<T> getJavaType() {
        return this.domainType;
    }


    @Override
    public String getEntityName() {

        Entity entity = domainType.getAnnotation(Entity.class);
        boolean hasName = null != entity && StringUtils.hasText(entity.name());

        return hasName ? entity.name() : domainType.getSimpleName();
    }


    @SuppressWarnings("unchecked")
    public Class<ID> getIdType() {
        return (Class<ID>) idMetadata.getType();
    }


    @Override
    public SingularAttribute<? super T, ?> getIdAttribute() {
        return idMetadata.getSimpleIdAttribute();
    }

    @Override
    public boolean hasCompositeId() {
        return !idMetadata.hasSimpleId();
    }

    @Override
    public Iterable<String> getIdAttributeNames() {

        List<String> attributeNames = new ArrayList<String>(idMetadata.attributes.size());

        for (SingularAttribute<? super T, ?> attribute : idMetadata.attributes) {
            attributeNames.add(attribute.getName());
        }

        return attributeNames;
    }

    private static class IdMetadata<T> implements Iterable<SingularAttribute<? super T, ?>> {

        private final IdentifiableType<T> type;
        private final Set<SingularAttribute<? super T, ?>> attributes;
        private Class<?> idType;

        @SuppressWarnings("unchecked")
        public IdMetadata(IdentifiableType<T> source) {

            this.type = source;
            this.attributes = (Set<SingularAttribute<? super T, ?>>) (source.hasSingleIdAttribute() ? Collections
                    .singleton(source.getId(source.getIdType().getJavaType())) : source.getIdClassAttributes());
        }

        public boolean hasSimpleId() {
            return attributes.size() == 1;
        }

        public Class<?> getType() {

            if (idType != null) {
                return idType;
            }

            // lazy initialization of idType field with tolerable benign data-race
            this.idType = tryExtractIdTypeWithFallbackToIdTypeLookup();

            return this.idType;
        }

        private Class<?> tryExtractIdTypeWithFallbackToIdTypeLookup() {

            try {
                Type<?> idType2 = type.getIdType();
                return idType2 == null ? fallbackIdTypeLookup(type) : idType2.getJavaType();
            } catch (IllegalStateException e) {
                // see https://hibernate.onjira.com/browse/HHH-6951
                return fallbackIdTypeLookup(type);
            }
        }

        private static Class<?> fallbackIdTypeLookup(IdentifiableType<?> type) {

            IdClass annotation = AnnotationUtils.findAnnotation(type.getJavaType(), IdClass.class);
            return annotation == null ? null : annotation.value();
        }

        public SingularAttribute<? super T, ?> getSimpleIdAttribute() {
            return attributes.iterator().next();
        }


        @Override
        public Iterator<SingularAttribute<? super T, ?>> iterator() {
            return attributes.iterator();
        }
    }
}
