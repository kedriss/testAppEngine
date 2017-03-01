package com.zenika.zencontact.resource;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.common.base.Optional;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.domain.blob.PhotoService;
import com.zenika.zencontact.persistence.UserDaoDataStore;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

/**
 * Created by kedriss on 28/02/17.
 */
@Component
@RestxResource
public class UserObjectifyResource {

    @GET("/v2/users")
    @PermitAll
    /**
     * Get the users defined in a arraylist : only mock objects
     */
    public Iterable<User> getAllUsers() {

        return UserDaoObjectify.getInstance().getAll();
    }

    @GET("/v2/users/{id}")
    @PermitAll
    public Optional<User> getUser(final Long id) {
        User user =null;
        try {
            user = UserDaoObjectify.getInstance().get(id);
            PhotoService.getInstance().prepareDownloadURL(user);
            PhotoService.getInstance().prepareUploadURL(user);

        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        return Optional.fromNullable(user);
    }

    @PUT("/v2/users/{id}")
    @PermitAll
    public Optional<User> updateUser(final Long id, final User user) {
        Long newId = UserDaoObjectify.getInstance().save(user);
        return getUser(newId);
    }

    @DELETE("/v2/users/{id}")
    @PermitAll
    public void deleteUser(final Long id) {

        UserDaoObjectify.getInstance().delete(id);
    }

    @POST("/v2/users")
    @PermitAll
    public User storeUser(final User user) {
        Long newId = UserDaoObjectify.getInstance().save(user);
        user.id= newId;
        return user;
    }

}
