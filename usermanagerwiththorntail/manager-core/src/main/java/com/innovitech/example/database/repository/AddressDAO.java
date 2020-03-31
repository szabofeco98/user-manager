package com.innovitech.example.database.repository;

import com.innovitech.example.database.entity.Address;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;

@Singleton
@Default
public class AddressDAO extends GenericDAO<Address> {

    public AddressDAO() {
        super(Address.class);
    }

}
