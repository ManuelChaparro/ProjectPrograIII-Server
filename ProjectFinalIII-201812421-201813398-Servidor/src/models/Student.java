package models;

import structures.AVLTree;

public class Student extends User{
	
	private AVLTree<Course> courseTree;
	private AVLTree<Activity> externalActivitiesTree;
	
	public Student(String nameUser, String code, String password) {
		super(nameUser, code, password);
	}
	
	public void addCourse(Course course) {
		
	}
	
//	public Course getCourse(String nameCourse) {
//		return new Course();
//	}
	
	public AVLTree<Course> getCourseList(){
		return courseTree;
	}
	
	public void addExternalActivity(ExternalActivity externalActivity) {
		
	}
	
//	public ExternalActivity getExternalActivity(String nameExActivity) {
//		
//	}
	
	public AVLTree<Activity> getExternalActivity(){
		return externalActivitiesTree;
	}

	public String toString() {
		return "Student [getNameUser()=" + getNameUser() + ", getCode()=" + getCode() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}
