package com.zenika.zencontact.persistence;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.zenika.zencontact.domain.User;

public interface UserDao {
	long save(User contact);
	void delete(Long id);
	User get(Long id) throws EntityNotFoundException;
	List<User> getAll();
}
