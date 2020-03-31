package com.innovitech.example.database.repository;


import com.innovitech.example.database.entity.User;

import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Singleton
@Default
public class UserDAO extends GenericDAO<User>{

    public UserDAO() {
        super(User.class);
    }

    @Transactional
    public User findByUsername(String username){
      Query sql= entityManager.createQuery("select u from User u where u.username =: username",User.class);
      sql.setParameter("username",username);
      return (User) sql.getSingleResult();
    }

    @Override
    @Transactional
    public void remove(User entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        if(entity.getAddresses()!=null) {
            entity.getAddresses().forEach(x -> entityManager.remove(
                    entityManager.contains(x) ? x : entityManager.merge(x)));
        }
    }
}
