package com.zenika.zencontact.resource;

import com.google.appengine.repackaged.com.google.datastore.v1.Datastore;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserDao;
import com.zenika.zencontact.persistence.UserDaoDataStore;
import com.zenika.zencontact.persistence.UserRepository;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kedriss on 28/02/17.
 */
@Component
@RestxResource
public class UserDataStoreResource {

    @GET("/v1/users")
    @PermitAll
    /**
     * Get the users defined in a arraylist : only mock objects
     */
    public Iterable<User> getAllUsers() {

        return UserDaoDataStore.UserDaoDataStore().getAll();
    }

    @GET("/v1/users/{id}")
    @PermitAll
    public Optional<User> getUser(final Long id) {
        return Optional.fromNullable(UserDaoDataStore.UserDaoDataStore().get(id));
    }

    @PUT("/v1/users/{id}")
    @PermitAll
    public Optional<User> updateUser(final Long id, final User user) {
        Long newId = UserDaoDataStore.UserDaoDataStore().save(user);
        return getUser(newId);
    }

    @DELETE("/v1/users/{id}")
    @PermitAll
    public void deleteUser(final Long id) {

        UserDaoDataStore.UserDaoDataStore().delete(id);
    }

    @POST("/v1/users")
    @PermitAll
    public User storeUser(final User user) {
        Long newId = UserDaoDataStore.UserDaoDataStore().save(user);
        user.id= newId;
        return user;
    }

}
