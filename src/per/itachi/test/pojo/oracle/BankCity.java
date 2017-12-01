package per.itachi.test.pojo.oracle;

import java.time.LocalDateTime;

public class BankCity {
	
	private int id;
	
	private String code;
	
	private String name;
	
	private char status;
	
	private String creator;
	
	private LocalDateTime insertTime;
	
	private String editor;
	
	private LocalDateTime updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public LocalDateTime getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(LocalDateTime insertTime) {
		this.insertTime = insertTime;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
}
