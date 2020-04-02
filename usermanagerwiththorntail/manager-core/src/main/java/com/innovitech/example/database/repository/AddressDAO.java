package com.innovitech.example.database.repository;

import com.innovitech.example.database.entity.Address;
import com.innovitech.example.database.entity.User;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
@Default
public class AddressDAO extends GenericDAO<Address> {

    public AddressDAO() {
        super(Address.class);
    }

    @Transactional
    public List findByUser(Long id) {
        Query sql = entityManager.createQuery(
                "select a from Address a where a.user.id =: id");
        sql.setParameter("id", id);
        return sql.getResultList();
    }

    @Transactional
    public void removeByUser(User user) {
        Query sql = entityManager.createQuery(
                "DELETE from Address a where a.user.id =: id");
        sql.setParameter("id", user.getId());
        sql.executeUpdate();
    }
}
