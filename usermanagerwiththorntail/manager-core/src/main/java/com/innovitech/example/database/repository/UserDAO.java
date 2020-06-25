package com.innovitech.example.database.repository;

import java.util.ArrayList;
import java.util.List;

import com.innovitech.example.database.entity.User;
import com.innovitech.example.domain.SearchUserQueryRequest;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@Singleton @Default public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    @Transactional public User findByUsername(String username) {
        Query sql = entityManager.createQuery("select u from User u where u.username =: username", User.class);
        sql.setParameter("username", username);
        return (User) sql.getSingleResult();
    }

    public List<User> getUsers(SearchUserQueryRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = criteriaBuilder.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root);
        if (request.getSortType().equals("asc")) {
            cq.orderBy(criteriaBuilder.asc(root.get(request.getSortableElem())));
        } else {
            cq.orderBy(criteriaBuilder.desc(root.get(request.getSortableElem())));
        }

        int first = request.getLimit() * request.getPage();
        TypedQuery<User> query = entityManager.createQuery(cq).setFirstResult(first).setMaxResults(request.getLimit());
        return query.getResultList();
    }

    public long getUserCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(User.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
