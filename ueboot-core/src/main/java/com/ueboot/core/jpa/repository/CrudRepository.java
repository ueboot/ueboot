package com.ueboot.core.jpa.repository;


import com.ueboot.core.jpa.repository.query.StringQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author xiangli.ma
 * @since 1.0
 */
public interface CrudRepository<T, ID extends Serializable> extends Repository {

    <S extends T> void save(S entity);


    <S extends T> void save(Collection<S> entities);

    <S extends T> void update(S entity);

    <S extends T> void update(Collection<S> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T get(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    boolean exists(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return altities
     */
    List<T> findAll();

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();


    long countBy(StringQuery stringQuery);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(ID id);


    void delete(T entity);


    void delete(Collection<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();
}
