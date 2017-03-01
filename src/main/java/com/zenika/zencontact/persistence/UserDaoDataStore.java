package com.zenika.zencontact.persistence;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.datastore.v1.Datastore;
import com.zenika.zencontact.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kedriss on 28/02/17.
 */
public class UserDaoDataStore implements UserDao {

    private static UserDaoDataStore INSTANCE = new UserDaoDataStore();
    public  static  UserDaoDataStore UserDaoDataStore(){
     return INSTANCE;
    }

    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public long save(User contact) {
        Entity e = new Entity("User");
        if(contact.id !=null){
            Key key = KeyFactory.createKey("User", contact.id);
            try {
                e = datastore.get(key);
            } catch (EntityNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        e.setProperty("firstname", contact.firstName);
        e.setProperty("lastname", contact.lastName);
        if(contact.birthdate!=null)e.setProperty("birthdate", contact.birthdate);
        e.setProperty("email", contact.email);
        e.setProperty("password", contact.password);

       Key newKey =  datastore.put(e);
        return newKey.getId();
    }

    @Override
    public void delete(Long id) {
       Key key = KeyFactory.createKey("User",id);
        datastore.delete(key);
    }

    @Override
    public User get(Long id)  {
        Entity e = null;
        try {
            e = datastore.get(KeyFactory.createKey("User",id));
        } catch (EntityNotFoundException e1) {
            e1.printStackTrace();
        }

        return createUser(e);
    }

    private User createUser(Entity e) {
        return User.create().id(e.getKey().getId())
                .firstName((String)e.getProperty("firstname"))
                .lastName((String)e.getProperty("lastname"))
                //.birthdate((Date)e.getProperty("birthdate"))
                .email((String)e.getProperty("email"))
                .password((String)e.getProperty("password"));
    }

    @Override
    public List<User> getAll() {
        List<User>  contacts = new ArrayList<>();

       Query query = new Query("User").
               addProjection(new PropertyProjection("firstname",String.class)).
               addProjection(new PropertyProjection("lastname",String.class)).
               addProjection(new PropertyProjection("email",String.class)).
               addProjection(new PropertyProjection("password",String.class))
//               addProjection(new PropertyProjection("birtdate",Date.class))
               ;

        PreparedQuery pq = datastore.prepare(query);
        for(Entity e : pq.asIterable()){
            System.out.println(e);
            contacts.add(createUser(e));
        }
        return contacts;
    }
}
