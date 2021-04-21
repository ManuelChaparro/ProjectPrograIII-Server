package models;

import java.util.Iterator;

import structures.DoubleList;

public class Course extends Activity{
	
	private String nameCourseTeacher;
	private DoubleList<Homework> homeworkList;
	
	public Course(String nameCourse, String descriptionCourse, String scheduleCourse) {
		super(nameCourse, descriptionCourse, scheduleCourse);
	}

	public String getNameCourseTeacher() {
		return nameCourseTeacher;
	}

	public void setNameCourseTeacher(String nameCourseTeacher) {
		this.nameCourseTeacher = nameCourseTeacher;
	}
	
	public void addHomework(Homework homework) {
		homeworkList.insert(homework);
	}
	
	public void deleteHomework(String nameHomework) {
		homeworkList.remove(new Homework(nameHomework));
	}

	public void modifyHomework(String nameHomework, String annotation, double calification) {
		getHomework(nameHomework).setAnnotation(annotation);
		getHomework(nameHomework).setCalification(calification);
	}
	
	public DoubleList<Homework> getHomeworkList() {
		return homeworkList;
	}
	
	public Homework getHomework(String nameHomework) {
		Iterator<Homework> it = homeworkList.iterator();
		while (it.hasNext()) {
			if(it.next().getNameHomework().equalsIgnoreCase(nameHomework)) {
				return it.next();
			}
		}
		return null;
	}
	
	
}
