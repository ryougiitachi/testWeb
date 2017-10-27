package per.itachi.test.dao.postgresql;

import java.util.List;

import per.itachi.test.pojo.postgresql.User;

public interface UserDao extends GenericDao<User, Long> {
	
	List<User> findUsersByHQL();
	
	User findUserByQBC(Long id);
	
	User findUserByQBE(Long id);

}
