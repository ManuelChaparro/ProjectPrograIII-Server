package models;

public class Teacher extends User{

	public Teacher(String nameUser, String code, String password) {
		super(nameUser, code, password);
	}
	
	public Teacher(String code) {
		super(code);
	}

	public String toString() {
		return "Teacher [getNameUser()=" + getNameUser() + ", getCode()=" + getCode() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}
