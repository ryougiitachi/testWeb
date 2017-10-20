package per.itachi.test.pojo.postgresql;

import java.sql.Timestamp;

public class User {
	
	private long userID;
	
	private String username;
	
	private Timestamp insertDate;

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

	public Timestamp getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}
}
