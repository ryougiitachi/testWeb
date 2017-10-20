package per.itachi.test.service;

import java.util.List;

import per.itachi.test.pojo.postgresql.User;

public interface UserService {
	
	User load(long id);

	User get(long id);

	List<User> findAll();

	void persist(User entity);

	long save(User entity);

	void saveOrUpdate(User entity);

	void delete(long id);

	void flush();

}
