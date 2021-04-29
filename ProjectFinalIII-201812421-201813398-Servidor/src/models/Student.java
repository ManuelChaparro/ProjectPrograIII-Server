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
			throw new Exception("La asignatura que desea inscribir ya existe.");
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

	private void initArrayCourseList() {
		if (courseList == null) {
			courseList = new ArrayList<Course>();
		}
	}

	public void cancelCourse(String nameCourse) throws Exception {
		if (validateExistCourse(nameCourse)) {
			for (Course course : courseList) {
				if (course.getNameActivity().equalsIgnoreCase(nameCourse)) {
					courseList.remove(course);
				}
			}
		} else {
			throw new Exception("El curso que desea eliminar no existe");
		}
	}

	public Course getCourse(String nameCourse) throws Exception {
		Course course = null;
		if (validateExistCourse(nameCourse)) {
			for (Course auxCourse : courseList) {
				if (auxCourse.getNameActivity().equalsIgnoreCase(nameCourse)) {
					course = auxCourse;
				}
			}
		} else {
			throw new Exception("El curso que busca no existe.");
		}
		return course;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void addHomework(String nameCourse, String nameHomework, String annotationHomework, double calification)
			throws Exception {
		Course course = getCourse(nameCourse);
		course.addHomework(new Homework(nameHomework, annotationHomework, calification));
	}

	private void initArrayExActivities() {
		if (externalActivitiesList == null) {
			externalActivitiesList = new ArrayList<ExternalActivity>();
		}
	}

	private boolean validateExistExAct(String nameExActivity) {
		boolean exist = false;
		for (int i = 0; i < externalActivitiesList.size(); i++) {
			if (externalActivitiesList.get(i).getNameActivity().equalsIgnoreCase(nameExActivity)) {
				exist = true;
			}
		}
		return exist;
	}

	public void addExternalActivity(ExternalActivity externalActivity) throws Exception {
		initArrayExActivities();
		if (!validateExistExAct(externalActivity.getNameActivity())) {
			externalActivitiesList.add(externalActivity);
		} else {
			throw new Exception("La actividad externa que desea anadir ya existe.");
		}
	}

	public void modifyExternalActivityDescription(String nameExActivity, String descriptionExActivity)
			throws Exception {
		getExternalActivity(nameExActivity).setDescriptionActivity(descriptionExActivity);
	}

	public void deleteExternalActivity(String nameExActivity) throws Exception {
		if (validateExistExAct(nameExActivity)) {
			for (ExternalActivity exActivity : externalActivitiesList) {
				if (exActivity.getNameActivity().equalsIgnoreCase(nameExActivity)) {
					externalActivitiesList.remove(exActivity);
				}
			}
		} else {
			throw new Exception("La actividad externa que desea eliminar no existe.");
		}
	}

	public ExternalActivity getExternalActivity(String nameExActivity) throws Exception {
		if (validateExistExAct(nameExActivity)) {
			for (ExternalActivity exActivity : externalActivitiesList) {
				if (exActivity.getNameActivity().equalsIgnoreCase(nameExActivity)) {
					return exActivity;
				}
			}
		} else {
			throw new Exception("La actividad que busca no existe.");
		}
		return null;
	}

	public ArrayList<ExternalActivity> getExternalActivityList() {
		return externalActivitiesList;
	}

	public double calculateAvgCourseCalification(String nameCourse) throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCalification = 0;
		Course course = getCourse(nameCourse);
		if (course.getNameActivity().equalsIgnoreCase(nameCourse)) {
			for (Homework homework : course.getHomeworkList()) {
				quantityCalification++;
				sumatoryCalification += homework.getCalification();
			}
			result = sumatoryCalification / quantityCalification;
			return result;
		} else {
			throw new Exception("El curso que desea calcular el promedio, no existe.");
		}
	}

	public double calculateTotalAvgCalification() throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCourses = 0;
		for (Course course : courseList) {
			quantityCourses++;
			sumatoryCalification += calculateAvgCourseCalification(course.getNameActivity());
		}
		result = sumatoryCalification / quantityCourses;
		return result;
	}

	public String toString() {
		return "Student [getNameUser()=" + getNameUser() + ", getCode()=" + getCodeUser() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}
