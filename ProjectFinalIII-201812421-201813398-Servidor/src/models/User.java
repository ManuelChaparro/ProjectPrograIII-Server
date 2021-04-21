package models;

public class User {
	
	private String nameUser;
	private String code;
	private String password;
	
	public User(String nameUser, String code, String password) {
		this.nameUser = nameUser;
		this.code = code;
		this.password = password;
	}
	
	public User(String code) {
		this.code = code;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "User [nameUser=" + nameUser + ", code=" + code + ", password=" + password + "]";
	}
}