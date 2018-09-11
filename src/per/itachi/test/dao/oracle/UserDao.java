package per.itachi.test.dao.oracle;

import java.time.LocalDateTime;
import java.util.List;

import per.itachi.test.pojo.oracle.User;

public interface UserDao {
	
	User getUserByID(int userID);
	
	List<User> getUsersBeforeInsertTime(LocalDateTime insertTime);
	
	List<User> getUsersAfterInsertTime(LocalDateTime insertTime);
	
	int insertUser(User user);
}
