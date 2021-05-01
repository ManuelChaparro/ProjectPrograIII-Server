package models;

import java.util.ArrayList;

public class Student extends User {

	private ArrayList<Course> courseList;
	private ArrayList<ExternalActivity> externalActivitiesList;

	public Student(String nameStudent, String codeStudent, String password) {
		super(nameStudent, codeStudent, password);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
	}

	public Student(String codeStudent) {
		super(codeStudent);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
	}

	public void addCourse(Course course) throws Exception {
		initArrayCourseList();
		if (!validateExistCourse(course.getNameActivity())) {
			courseList.add(course);
		} else {
			throw new Exception();
		}
	}

	public void cancelCourse(String nameCourse) throws Exception {
		if (validateExistCourse(nameCourse)) {
			for (int i = 0; i < courseList.size(); i++) {
				if (courseList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
					courseList.remove(courseList.get(i));
				}
			}
		} else {
			throw new Exception();
		}
	}

	public Course getCourse(String nameCourse) throws Exception {
		Course course = null;
		if (validateExistCourse(nameCourse)) {
			for (int i = 0; i < courseList.size(); i++) {
				if (courseList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
					course = courseList.get(i);
				}
			}
		} else {
			throw new Exception();
		}
		return course;
	}

	public ArrayList<Course> getCourseList() {
		if (courseList == null) {
			courseList = new ArrayList<Course>();
			return courseList;
		} else {
			return courseList;
		}
	}

	private boolean validateExistCourse(String nameCourse) {
		boolean exist = false;
		for (int i = 0; i < courseList.size(); i++) {
			if (courseList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
				exist = true;
			}
		}
		return exist;
	}

	public void addHomework(String nameCourse, String nameHomework, String annotationHomework, double calification)
			throws Exception {
		getCourse(nameCourse).addHomework(new Homework(nameHomework, annotationHomework, calification));
	}

	public void cancelHomework(String nameCourse, String nameHomework) throws Exception {
		getCourse(nameCourse).deleteHomework(nameHomework);
	}

	public void addExternalActivity(ExternalActivity externalActivity) throws Exception {
		initArrayExActivities();
		if (!validateExistExActivity(externalActivity.getNameActivity())) {
			externalActivitiesList.add(externalActivity);
		} else {
			throw new Exception();
		}
	}

	public void modifyExternalActivity(String nameExActivity, String descriptionExActivity, String schedulerExActivity)
			throws Exception {
		getExternalActivity(nameExActivity).setDescriptionActivity(descriptionExActivity);
		getExternalActivity(nameExActivity).setScheduleActivity(schedulerExActivity);
	}

	public void cancelExternalActivity(String nameExActivity) throws Exception {
		if (validateExistExActivity(nameExActivity)) {
			for (int i = 0; i < externalActivitiesList.size(); i++) {
				if (externalActivitiesList.get(i).getNameActivity().equalsIgnoreCase(nameExActivity)) {
					externalActivitiesList.remove(externalActivitiesList.get(i));
				}
			}
		} else {
			throw new Exception();
		}
	}

	public ExternalActivity getExternalActivity(String nameExActivity) throws Exception {
		if (validateExistExActivity(nameExActivity)) {
			for (int i = 0; i < externalActivitiesList.size(); i++) {
				if (externalActivitiesList.get(i).getNameActivity().equalsIgnoreCase(nameExActivity)) {
					return externalActivitiesList.get(i);
				}
			}
		} else {
			throw new Exception();
		}
		return null;
	}

	public ArrayList<ExternalActivity> getExternalActivityList() {
		if (externalActivitiesList == null) {
			externalActivitiesList = new ArrayList<>();
			return externalActivitiesList;
		} else {
			return externalActivitiesList;
		}
	}

	private boolean validateExistExActivity(String nameExActivity) {
		boolean exist = false;
		for (int i = 0; i < externalActivitiesList.size(); i++) {
			if (externalActivitiesList.get(i).getNameActivity().equalsIgnoreCase(nameExActivity)) {
				exist = true;
			}
		}
		return exist;
	}

	public double calculateAvgCourseCalification(String nameCourse) throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCalification = 0;
		ArrayList<Homework> homeworkList = getCourse(nameCourse).getHomeworkList();
		for (int i = 0; i < homeworkList.size(); i++) {
			quantityCalification++;
			sumatoryCalification += homeworkList.get(i).getCalification();
		}
		result = sumatoryCalification / quantityCalification;
		return result;
	}

	public double calculateTotalAvgCalification() throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCourses = 0;
		for (int i = 0; i < courseList.size(); i++) {
			quantityCourses++;
			sumatoryCalification += calculateAvgCourseCalification(courseList.get(i).getNameActivity());
		}
		result = sumatoryCalification / quantityCourses;
		return result;
	}

	private void initArrayCourseList() {
		if (courseList == null) {
			courseList = new ArrayList<Course>();
		}
	}

	private void initArrayExActivities() {
		if (externalActivitiesList == null) {
			externalActivitiesList = new ArrayList<ExternalActivity>();
		}
	}

	public String toString() {
		return "Student [getNameUser()=" + getNameUser() + ", getCode()=" + getCodeUser() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}
