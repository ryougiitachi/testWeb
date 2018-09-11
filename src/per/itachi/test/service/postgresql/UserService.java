package per.itachi.test.service.postgresql;

import java.util.List;

import per.itachi.test.pojo.postgresql.User;

public interface UserService {
	
	User load(long id);

	User get(long id);
	
	User findUser(long id, int type);

	List<User> findAll(int type);
	
	List<User> findUsers(long id, int type);

	void persist(User entity);

	long save(User entity);
	
	void update(User entity);

	void saveOrUpdate(User entity);

	void delete(long id);

	void flush();

	void testTrxPersistence();
	
	void testSessionCache();
	
	void testSessionFactoryCache();
}
