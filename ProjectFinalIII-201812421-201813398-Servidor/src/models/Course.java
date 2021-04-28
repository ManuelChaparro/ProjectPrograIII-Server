package models;

import java.util.ArrayList;

public class Course extends Activity {

	private String nameCourseTeacher;
	private ArrayList<Homework> homeworkList;

	public Course(String nameCourse, String nameTeacher, String descriptionCourse, String scheduleCourse) {
		super(nameCourse, descriptionCourse, scheduleCourse);
		this.nameCourseTeacher = nameTeacher;
		homeworkList = new ArrayList<Homework>();
	}

	public Course(String nameCourse) {
		super(nameCourse);
		this.nameCourseTeacher = "";
		homeworkList = new ArrayList<Homework>();
	}

	public void setNameCourseTeacher(String nameCourseTeacher) {
		this.nameCourseTeacher = nameCourseTeacher;
	}

	public String getNameCourseTeacher() {
		return nameCourseTeacher;
	}

	public void addHomework(Homework homework) {
		homeworkList.add(homework);
	}

	public void deleteHomework(String nameHomework) {
		for (Homework homework : homeworkList) {
			if (homework.getNameHomework().equalsIgnoreCase(nameHomework)) {
				homeworkList.remove(homework);
			}
		}
	}

	public void modifyHomeworkAnnotation(String nameHomework, String annotation) throws Exception {
		getHomework(nameHomework).setAnnotation(annotation);
	}

	public void modifyHomeworkCalification(String nameHomework, double calification) throws Exception {
		getHomework(nameHomework).setCalification(calification);
	}

	public ArrayList<Homework> getHomeworkList() {
		return homeworkList;
	}

	public Homework getHomework(String nameHomework) throws Exception {
		Homework auxHomework = null;
		for (Homework homework : homeworkList) {
			if (homework.getNameHomework().equalsIgnoreCase(nameHomework)) {
				auxHomework = homework;
			}
		}
		return auxHomework;
	}

	@Override
	public String toString() {
		return getNameActivity() + "&" + getNameCourseTeacher() + "&" + getDescriptionActivity() + "&"
				+ getScheduleActivity();
	}
}