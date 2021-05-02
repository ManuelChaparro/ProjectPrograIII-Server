package models;

import java.util.ArrayList;

public class Student extends User {

	private int[][] sheduleAvailable;
	private ArrayList<Course> courseList;
	private ArrayList<ExternalActivity> externalActivitiesList;

	public Student(String nameStudent, String codeStudent, String password) {
		super(nameStudent, codeStudent, password);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
		sheduleAvailable = new int[15][7];
		initMatrix();
	}

	private void initMatrix() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 7; j++) {
				sheduleAvailable[i][j] = 0;
			}
		}
	}

	public Student(String codeStudent) {
		super(codeStudent);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
		sheduleAvailable = new int[15][7];
		initMatrix();
	}

	public void addCourse(Course course) throws Exception {
		initArrayCourseList();
		String[] infoCourse = course.toString().split("&")[3].split("%");
		if (!validateExistCourse(course.getNameActivity()) && isAvailableSchedule(infoCourse)) {
			forScheduleMatrix(infoCourse);
			courseList.add(course);
		} else {
			throw new Exception();
		}
	}

	private void forScheduleMatrix(String[] schedule) {
		for (int i = 0; i < schedule.length; i++) {
			switch (schedule[i].split("#")[0]) {
			case "LUNES":
				setScheduleMatrix(schedule[i], 0);
				break;
			case "MARTES":
				setScheduleMatrix(schedule[i], 1);
				break;
			case "MIERCOLES":
				setScheduleMatrix(schedule[i], 2);
				break;
			case "JUEVES":
				setScheduleMatrix(schedule[i], 3);
				break;
			case "VIERNES":
				setScheduleMatrix(schedule[i], 4);
				break;
			case "SABADO":
				setScheduleMatrix(schedule[i], 5);
				break;
			case "DOMINGO":
				setScheduleMatrix(schedule[i], 6);
				break;
			default:
				break;
			}
		}
	}

	private boolean isAvailableSchedule(String[] schedule) {
		boolean isAvailable = true;
		for (int i = 0; i < schedule.length; i++) {
			switch (schedule[i].split("#")[0]) {
			case "LUNES":
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 0);
				break;
			case "MARTES":
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 1);
				break;
			case "MIERCOLES":
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 2);
				break;
			case "JUEVES":
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 3);
				break;
			case "VIERNES":
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 4);
				break;
			case "SABADO":
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 5);
				break;
			case "DOMINGO":
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 6);
				break;
			default:
				break;
			}
		}
		return isAvailable;
	}

	private boolean isScheduleMatrix(String schedule, boolean isAvailable, int k) {
		int init = Integer.parseInt(schedule.split("#")[1]);
		int end = Integer.parseInt(schedule.split("#")[2]);
		for (int j = 0; j < end - init; j++) {
			if (sheduleAvailable[(init + j) - 6][k] == 1) {
				isAvailable = false;
			}
		}
		return isAvailable;
	}

	private void setScheduleMatrix(String schedule, int k) {
		int init = Integer.parseInt(schedule.split("#")[1]);
		int end = Integer.parseInt(schedule.split("#")[2]);
		for (int j = 0; j < end - init; j++) {
			sheduleAvailable[(init + j) - 6][k] = 1;
		}
	}

	public void cancelCourse(String nameCourse) throws Exception {
		if (validateExistCourse(nameCourse)) {
			for (int i = 0; i < courseList.size(); i++) {
				if (courseList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
					forDeleteMatrix(courseList.get(i).toString().split("&")[3].split("%"));
					courseList.remove(courseList.get(i));
				}
			}
		} else {
			throw new Exception();
		}
	}

	private void forDeleteMatrix(String[] schedule) {
		for (int i = 0; i < schedule.length; i++) {
			switch (schedule[i].split("#")[0]) {
			case "LUNES":
				deleteScheduleMatrix(schedule[i], 0);
				break;
			case "MARTES":
				deleteScheduleMatrix(schedule[i], 1);
				break;
			case "MIERCOLES":
				deleteScheduleMatrix(schedule[i], 2);
				break;
			case "JUEVES":
				deleteScheduleMatrix(schedule[i], 3);
				break;
			case "VIERNES":
				deleteScheduleMatrix(schedule[i], 4);
				break;
			case "SABADO":
				deleteScheduleMatrix(schedule[i], 5);
				break;
			case "DOMINGO":
				deleteScheduleMatrix(schedule[i], 6);
				break;
			default:
				break;
			}
		}
	}

	private void deleteScheduleMatrix(String schedule, int k) {
		int init = Integer.parseInt(schedule.split("#")[1]);
		int end = Integer.parseInt(schedule.split("#")[2]);
		for (int j = 0; j < end - init; j++) {
			sheduleAvailable[(init + j) - 6][k] = 0;
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
		String[] infoActivity = externalActivity.toString().split("&")[2].split("%");
		for (String string : infoActivity) {
			System.out.println(string);
		}
		if (!validateExistExActivity(externalActivity.getNameActivity()) && isAvailableSchedule(infoActivity)) {
			forScheduleMatrix(infoActivity);
			externalActivitiesList.add(externalActivity);
		} else {
			throw new Exception();
		}
	}

	public void modifyExternalActivity(String nameExActivity, String descriptionExActivity, String schedulerExActivity)
			throws Exception {
		String[] infoSchedule = new String[1];
		infoSchedule[0] = schedulerExActivity;
		System.out.println("aqui: "+isAvailableSchedule(infoSchedule));
		if (isAvailableSchedule(infoSchedule)) {
			forScheduleMatrix(infoSchedule);
			getExternalActivity(nameExActivity).setDescriptionActivity(descriptionExActivity);
			getExternalActivity(nameExActivity).setScheduleActivity(schedulerExActivity);
		}else {
			throw new Exception();
		}
	}

	public void cancelExternalActivity(String nameExActivity) throws Exception {
		if (validateExistExActivity(nameExActivity)) {
			for (int i = 0; i < externalActivitiesList.size(); i++) {
				if (externalActivitiesList.get(i).getNameActivity().equalsIgnoreCase(nameExActivity)) {
					forDeleteMatrix(externalActivitiesList.get(i).toString().split("&")[2].split("%"));
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
			double avgCourse = calculateAvgCourseCalification(courseList.get(i).getNameActivity());
			if (avgCourse >= 0.0) {
				quantityCourses++;
				sumatoryCalification += avgCourse;
			}
		}
		result = sumatoryCalification / quantityCourses;
		return result;
	}

	private void initArrayCourseList() {
		if (courseList == null) {
			courseList = new ArrayList<Course>();
		}
		if (sheduleAvailable == null) {
			sheduleAvailable = new int[15][7];
			initMatrix();
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
