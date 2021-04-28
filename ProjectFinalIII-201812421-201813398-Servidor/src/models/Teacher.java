package models;

public class Teacher extends User {

	public Teacher(String nameTeacher, String codeTeacher, String password) {
		super(nameTeacher, codeTeacher, password);
	}

	public Teacher(String codeTeacher) {
		super(codeTeacher);
	}

	public String toString() {
		return "Teacher [getNameUser()=" + getNameUser() + ", getCode()=" + getCodeUser() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}