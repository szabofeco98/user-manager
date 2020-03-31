package com.innovitech.example.database.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public abstract class GenericDAO<T> {
    protected Class<T> entityClass;
    @PersistenceContext(unitName = "persistence")
    protected EntityManager entityManager;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public List<T> findAll() {
        TypedQuery<T> typedQuery =
                entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass);
        return typedQuery.getResultList();
    }

    @Transactional
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Transactional()
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public T find(Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }
}
