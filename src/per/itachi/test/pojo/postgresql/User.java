package per.itachi.test.pojo.postgresql;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
	
	public static final String COLUMN_USER_ID = "USER_ID";
	public static final String COLUMN_USERNAME = "USERNAME";
	public static final String COLUMN_USER_TYPE = "USER_TYPE";
	public static final String COLUMN_INSERT_DATE = "INSERT_DATE";
	public static final String COLUMN_UPDATE_TIME = "UPDATE_TIME";
	
	public static final String PROPERTY_USER_ID = "userID";
	public static final String PROPERTY_USERNAME = "username";
	public static final String PROPERTY_USER_TYPE = "userType";
	public static final String PROPERTY_INSERT_DATE = "insertDate";
	public static final String PROPERTY_UPDATE_TIME = "updateTime";
	
	private long userID;
	
	private String username;
	
	/**
	 * 列类型为integer，如果列值为空加载表数据到int等基本类型的时候会报异常
	 * org.hibernate.PropertyAccessException: Null value was assigned to a property of 
	 * primitive type setter of per.itachi.test.pojo.postgresql.User.userType
	 * */
	private int userType;
	
	private Timestamp insertDate;
	
	private LocalDateTime updateTime;
	
	private LocalDateTime lastAccessDatetime;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public LocalDateTime getLastAccessDatetime() {
		return lastAccessDatetime;
	}

	public void setLastAccessDatetime(LocalDateTime lastAccessDatetime) {
		this.lastAccessDatetime = lastAccessDatetime;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", userType=" + userType + ", insertDate="
				+ insertDate + ", updateTime=" + updateTime + ", lastAccessDatetime=" + lastAccessDatetime + "]";
	}
}
