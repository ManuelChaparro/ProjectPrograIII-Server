package models;

import java.util.ArrayList;

public class Student extends User {

	private static final int DAYS = 7;
	private static final int HOURS = 15;
	private int[][] sheduleAvailable;
	private ArrayList<Course> courseList;
	private ArrayList<ExternalActivity> externalActivitiesList;

	public Student(String nameStudent, String codeStudent, String password) {
		super(nameStudent, codeStudent, password);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
		sheduleAvailable = new int[HOURS][DAYS];
		initMatrix();
	}

	public Student(String codeStudent) {
		super(codeStudent);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
		sheduleAvailable = new int[HOURS][DAYS];
		initMatrix();
	}

	public void addCourse(Course course) throws Exception {
		initArrayCourseList();
		String[] infoCourse = course.toString().split(ConstantsModels.SEPARATOR_Y_SPECIAL)[3]
				.split(ConstantsModels.SEPARATOR_PERCENT);
		if (!validateExistCourse(course.getNameActivity()) && isAvailableSchedule(infoCourse)) {
			travelScheduleMatrix(infoCourse);
			courseList.add(course);
		} else {
			throw new Exception();
		}
	}

	public void cancelCourse(String nameCourse) throws Exception {
		if (validateExistCourse(nameCourse)) {
			for (int i = 0; i < courseList.size(); i++) {
				if (courseList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
					deleteMatrix(courseList.get(i).toString().split(ConstantsModels.SEPARATOR_Y_SPECIAL)[3]
							.split(ConstantsModels.SEPARATOR_PERCENT));
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
		String[] infoActivity = externalActivity.toString().split(ConstantsModels.SEPARATOR_Y_SPECIAL)[2]
				.split(ConstantsModels.SEPARATOR_PERCENT);
		if (!validateExistExActivity(externalActivity.getNameActivity()) && isAvailableSchedule(infoActivity)) {
			travelScheduleMatrix(infoActivity);
			externalActivitiesList.add(externalActivity);
		} else {
			throw new Exception();
		}
	}

	public void modifyExternalActivity(String nameExActivity, String descriptionExActivity, String schedulerExActivity)
			throws Exception {
		String[] infoSchedule = new String[1];
		infoSchedule[0] = schedulerExActivity;
		if (isAvailableSchedule(infoSchedule)) {
			travelScheduleMatrix(infoSchedule);
			getExternalActivity(nameExActivity).setDescriptionActivity(descriptionExActivity);
			getExternalActivity(nameExActivity).setScheduleActivity(schedulerExActivity);
		} else {
			throw new Exception();
		}
	}

	public void cancelExternalActivity(String nameExActivity) throws Exception {
		if (validateExistExActivity(nameExActivity)) {
			for (int i = 0; i < externalActivitiesList.size(); i++) {
				if (externalActivitiesList.get(i).getNameActivity().equalsIgnoreCase(nameExActivity)) {
					deleteMatrix(externalActivitiesList.get(i).toString().split(ConstantsModels.SEPARATOR_Y_SPECIAL)[2]
							.split(ConstantsModels.SEPARATOR_PERCENT));
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
			sheduleAvailable = new int[HOURS][DAYS];
			initMatrix();
		}
	}

	private void initArrayExActivities() {
		if (externalActivitiesList == null) {
			externalActivitiesList = new ArrayList<ExternalActivity>();
		}
	}

	private void initMatrix() {
		for (int i = 0; i < HOURS; i++) {
			for (int j = 0; j < DAYS; j++) {
				sheduleAvailable[i][j] = 0;
			}
		}
	}

	private boolean isScheduleMatrix(String schedule, boolean isAvailable, int position) {
		int init = Integer.parseInt(schedule.split(ConstantsModels.SEPARATOR_NUMERAL)[1]);
		int end = Integer.parseInt(schedule.split(ConstantsModels.SEPARATOR_NUMERAL)[2]);
		for (int j = 0; j < end - init; j++) {
			if (sheduleAvailable[(init + j) - 6][position] == 1) {
				isAvailable = false;
			}
		}
		return isAvailable;
	}

	private void setScheduleMatrix(String schedule, int position) {
		int init = Integer.parseInt(schedule.split(ConstantsModels.SEPARATOR_NUMERAL)[1]);
		int end = Integer.parseInt(schedule.split(ConstantsModels.SEPARATOR_NUMERAL)[2]);
		for (int j = 0; j < end - init; j++) {
			sheduleAvailable[(init + j) - 6][position] = 1;
		}
	}

	private void deleteDataScheduleMatrix(String schedule, int position) {
		int init = Integer.parseInt(schedule.split(ConstantsModels.SEPARATOR_NUMERAL)[1]);
		int end = Integer.parseInt(schedule.split(ConstantsModels.SEPARATOR_NUMERAL)[2]);
		for (int j = 0; j < end - init; j++) {
			sheduleAvailable[(init + j) - 6][position] = 0;
		}
	}

	private void travelScheduleMatrix(String[] schedule) {
		for (int i = 0; i < schedule.length; i++) {
			switch (schedule[i].split(ConstantsModels.SEPARATOR_NUMERAL)[0]) {
			case ConstantsModels.MONDAY:
				setScheduleMatrix(schedule[i], 0);
				break;
			case ConstantsModels.TUESDAY:
				setScheduleMatrix(schedule[i], 1);
				break;
			case ConstantsModels.WEDNESDAY:
				setScheduleMatrix(schedule[i], 2);
				break;
			case ConstantsModels.THURSDAY:
				setScheduleMatrix(schedule[i], 3);
				break;
			case ConstantsModels.FRIDAY:
				setScheduleMatrix(schedule[i], 4);
				break;
			case ConstantsModels.SATURDAY:
				setScheduleMatrix(schedule[i], 5);
				break;
			case ConstantsModels.SUNDAY:
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
			switch (schedule[i].split(ConstantsModels.SEPARATOR_NUMERAL)[0]) {
			case ConstantsModels.MONDAY:
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 0);
				break;
			case ConstantsModels.TUESDAY:
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 1);
				break;
			case ConstantsModels.WEDNESDAY:
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 2);
				break;
			case ConstantsModels.THURSDAY:
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 3);
				break;
			case ConstantsModels.FRIDAY:
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 4);
				break;
			case ConstantsModels.SATURDAY:
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 5);
				break;
			case ConstantsModels.SUNDAY:
				isAvailable = isScheduleMatrix(schedule[i], isAvailable, 6);
				break;
			default:
				break;
			}
		}
		return isAvailable;
	}

	private void deleteMatrix(String[] schedule) {
		for (int i = 0; i < schedule.length; i++) {
			switch (schedule[i].split(ConstantsModels.SEPARATOR_NUMERAL)[0]) {
			case ConstantsModels.MONDAY:
				deleteDataScheduleMatrix(schedule[i], 0);
				break;
			case ConstantsModels.TUESDAY:
				deleteDataScheduleMatrix(schedule[i], 1);
				break;
			case ConstantsModels.WEDNESDAY:
				deleteDataScheduleMatrix(schedule[i], 2);
				break;
			case ConstantsModels.THURSDAY:
				deleteDataScheduleMatrix(schedule[i], 3);
				break;
			case ConstantsModels.FRIDAY:
				deleteDataScheduleMatrix(schedule[i], 4);
				break;
			case ConstantsModels.SATURDAY:
				deleteDataScheduleMatrix(schedule[i], 5);
				break;
			case ConstantsModels.SUNDAY:
				deleteDataScheduleMatrix(schedule[i], 6);
				break;
			default:
				break;
			}
		}
	}

	public String toString() {
		return "Student [getNameUser()=" + getNameUser() + ", getCode()=" + getCodeUser() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}