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

	public String getNameCourseTeacher() {
		return nameCourseTeacher;
	}

	public void addHomework(Homework homework) throws Exception {
		initArrayHomeworkList();
		if (!validateExistHomework(homework.getNameHomework())) {
			homeworkList.add(homework);
		} else {
			throw new Exception();
		}
	}

	public void deleteHomework(String nameHomework) throws Exception {
		if (validateExistHomework(nameHomework)) {
			for (int i = 0; i < homeworkList.size(); i++) {
				if (homeworkList.get(i).getNameHomework().equalsIgnoreCase(nameHomework)) {
					homeworkList.remove(homeworkList.get(i));
				}
			}
		} else {
			throw new Exception();
		}
	}
	
	public void modifyHomework(String nameHomework, String annotation, double calification) throws Exception {
		getHomework(nameHomework).setAnnotation(annotation);
		getHomework(nameHomework).setCalification(calification);
	}
	
	public boolean validateExistHomework(String nameHomework) {
		boolean exist = false;
		for (int i = 0; i < homeworkList.size(); i++) {
			if (homeworkList.get(i).getNameHomework().equalsIgnoreCase(nameHomework)) {
				exist = true;
			}
		}
		return exist;
	}

	public ArrayList<Homework> getHomeworkList() {
		return homeworkList;
	}

	public Homework getHomework(String nameHomework) throws Exception {
		Homework auxHomework = null;
		if (validateExistHomework(nameHomework)) {
			for (int i = 0; i < homeworkList.size(); i++) {
				if (homeworkList.get(i).getNameHomework().equalsIgnoreCase(nameHomework)) {
					auxHomework = homeworkList.get(i);
				}
			}
		} else {
			throw new Exception();
		}
		return auxHomework;
	}

	private void initArrayHomeworkList() {
		if (homeworkList == null) {
			homeworkList = new ArrayList<Homework>();
		}
	}

	public String toStringVariant() {
		return getNameActivity() + "&" + getDescriptionActivity() + "&" + getScheduleActivity();
	}
	
	public String toString() {
		return getNameActivity() + "&" + getNameCourseTeacher() + "&" + getDescriptionActivity() + "&"
				+ getScheduleActivity();
	}
}