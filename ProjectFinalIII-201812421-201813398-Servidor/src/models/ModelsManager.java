package models;

import persistence.GSONFileManager;
import structures.AVLTree;
import structures.DoubleList;

public class ModelsManager {
	
	private AVLTree<Student> studentTree;
	private AVLTree<Teacher> teacherTree;
	private DoubleList<Course> courseGeneralList;
	private GSONFileManager gsonManager;
	
	public ModelsManager() {
		
	}
	
	public void createStudent(String nameStudent, String code, String password){
		
	}
	
	public void createTeacher(String nameStudent, String code, String password) {
		
	}
	
	public Student getStudent(String code) {
		
	}
	
	public Teacher getTeacher(String code) {
		
	}
	
	public void addCourse(Course course) {
		
	}
	
	public Course getCourse(String nameCourse) {
		
	}
	
	public void addExternalActivity(String nameExActivity, String description, String schedule) {
		
	}
	
	public ExternalActivity getExternalActivity(String nameExActivity) {
		
	}
	
	public double calculateAvgCalification(String code) {
		
	}
	
	public void addCourseGeneralList(Course course) {
		
	}
	
	public DoubleList<Course> getCourseGeneralList(){
		
	}
	
	public void manageCourseAdmin() {
		
	}
}