package per.itachi.test.dao.postgresql;

import java.util.Iterator;
import java.util.List;

import per.itachi.test.pojo.postgresql.User;

public interface UserDao extends GenericDao<User, Long> {
	
	List<User> findUsersByHQL();
	
	List<User> findUsersByJPACriteria();
	
	List<User> findUsersByNativeQuery();
	
	List<User> findUsersByNamedQuery();
	
	List<User> findUsersByNamedNativeQuery();
	
	List<User> findUsersByNamedNativeQuery(Long id);
	
	Iterator<User> findUsersByIterate();
	
	User findUserByQBC(Long id);
	
	User findUserByQBE(Long id);

	User findUserByNamedQuery(Long id);
}
