package per.itachi.test.service.oracle;

import java.time.LocalDateTime;
import java.util.List;

import per.itachi.test.pojo.oracle.User;

public interface UserService {
	
	User getUserByID(int userID);
	
	List<User> getUsersBeforeInsertTime(LocalDateTime insertTime);
	
	List<User> getUsersAfterInsertTime(LocalDateTime insertTime);
	
	int insertUser(User user);

}
