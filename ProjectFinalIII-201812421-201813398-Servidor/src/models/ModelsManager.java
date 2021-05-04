package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import persistence.ArchiveClass;
import persistence.GSONFileManager;
import structures.AVLTree;

public class ModelsManager {

	private ArchiveClass archiveClass;
	private AVLTree<Student> studentTree;
	private AVLTree<Teacher> teacherTree;
	private AVLTree<String> availableCourses;
	private ArrayList<Course> courseGeneralList;

	public ModelsManager() {
		loadDataJSon();
	}

	public void createStudent(Student student) throws Exception {
		if (!studentTree.exist(student)) {
			studentTree.insert(student);
		} else {
			throw new Exception();
		}
	}

	public String getStudentName(String codeStudent) throws Exception {
		return getStudent(codeStudent).getNameUser();
	}

	public String getTeacherName(String codeTeacher) throws Exception {
		return getTeacher(codeTeacher).getNameUser();
	}

	public boolean validateStudentLogin(String codeStudent, String password) {
		Iterator<Student> itStudent = studentTree.inOrder();
		while (itStudent.hasNext()) {
			Student student = itStudent.next();
			if (student.getCodeUser().equalsIgnoreCase(codeStudent) && student.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public boolean isExistStudent(String codeStudent) {
		return studentTree.exist(new Student(codeStudent));
	}

	public boolean isExistTeacher(String codeTeacher) {
		return teacherTree.exist(new Teacher(codeTeacher));
	}

	private Student getStudent(String codeStudent) throws Exception {
		Student student = new Student(codeStudent);
		if (studentTree.exist(student)) {
			return studentTree.find(student);
		} else {
			throw new Exception();
		}
	}

	private Teacher getTeacher(String codeTeacher) throws Exception {
		Teacher teacher = new Teacher(codeTeacher);
		if (teacherTree.exist(teacher)) {
			return teacherTree.find(teacher);
		} else {
			System.out.println("else");
			return teacherTree.find(teacher);
		}
	}

	public void createTeacher(Teacher teacher) throws Exception {
		if (!teacherTree.exist(teacher)) {
			teacherTree.insert(teacher);
		} else {
			throw new Exception();
		}
	}

	public boolean validateTeacherLogin(String codeTeacher, String password) {
		Iterator<Teacher> itTeacher = teacherTree.inOrder();
		while (itTeacher.hasNext()) {
			Teacher teacher = itTeacher.next();
			if (teacher.getCodeUser().equalsIgnoreCase(codeTeacher) && teacher.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public String getAvailableCourses() throws Exception {
		String courses = ConstantsModels.EMPTY_STRING;
		Iterator<String> itAvailableCourses = availableCourses.inOrder();
		while (itAvailableCourses.hasNext()) {
			courses += itAvailableCourses.next() + ConstantsModels.SEPARATOR_DOT_AND_COMA;

		}
		return courses;
	}

	public String getAvailableCoursesTH() throws Exception {
		String courses = ConstantsModels.EMPTY_STRING;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase("")) {
				courses += courseGeneralList.get(i).getNameActivity() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
			}
		}
		return courses;
	}
	
	public String getCoursesTH(String nameTeacher) throws Exception {
		String courses = ConstantsModels.EMPTY_STRING;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				courses += courseGeneralList.get(i).getNameActivity() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
			}
		}
		return courses;
	}
	
	public String getSpecificCourseTH(String nameTeacher) throws Exception {
		String courses = ConstantsModels.EMPTY_STRING;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				courses += courseGeneralList.get(i).toString();
			}
		}
		return courses;
	}

	public String getAvailableTeachers(String nameCourse) {
		String teachers = ConstantsModels.EMPTY_STRING;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse) && !courseGeneralList.get(i)
					.getNameCourseTeacher().equalsIgnoreCase(ConstantsModels.EMPTY_STRING)) {
				teachers += courseGeneralList.get(i).getNameCourseTeacher() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
			}
		}
		return teachers;
	}

	public void addStudentHomework(String codeStudent, String nameCourse, String nameHomework,
			String annotationHomework, double calification) throws Exception {
		if (!nameHomework.equalsIgnoreCase(ConstantsModels.EMPTY_STRING)) {
			getStudent(codeStudent).addHomework(nameCourse, nameHomework, annotationHomework, calification);
		} else {
			throw new Exception();
		}
	}

	public String getStudentSpecifiCourse(String codeStudent, String nameCourse) throws Exception {
		String courses = ConstantsModels.EMPTY_STRING;
		ArrayList<Course> coursesList = getStudent(codeStudent).getCourseList();
		for (int i = 0; i < coursesList.size(); i++) {
			if (coursesList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
				courses = coursesList.get(i).toString() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
			}
		}
		return courses;
	}

	public String getStudentCompleteCourses(String codeStudent) throws Exception {
		String courses = ConstantsModels.EMPTY_STRING;
		ArrayList<Course> coursesList = getStudent(codeStudent).getCourseList();
		for (int i = 0; i < coursesList.size(); i++) {
			courses += coursesList.get(i).toStringVariant() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
		}
		return courses;
	}

	public String getStudentCourses(String codeStudent) throws Exception {
		String courses = ConstantsModels.EMPTY_STRING;
		ArrayList<Course> coursesList = getStudent(codeStudent).getCourseList();
		for (int i = 0; i < coursesList.size(); i++) {
			courses += coursesList.get(i).getNameActivity() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
		}
		return courses;
	}

	public String getStudentHomeworks(String codeStudent, String nameCourse) throws Exception {
		String homeworks = ConstantsModels.EMPTY_STRING;
		ArrayList<Homework> homeworksList = getStudent(codeStudent).getCourse(nameCourse).getHomeworkList();
		for (int i = 0; i < homeworksList.size(); i++) {
			homeworks += homeworksList.get(i).getNameHomework() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
		}
		return homeworks;
	}

	public String getSpecificStudentHomework(String codeStudent, String nameCourse, String nameHomework)
			throws Exception {
		return getStudent(codeStudent).getCourse(nameCourse).getHomework(nameHomework).toString();
	}

	public void modifySpecificHomework(String codeStudent, String nameCourse, String nameHomework,
			String annotationHomework, double calification) throws Exception {
		getStudent(codeStudent).getCourse(nameCourse).modifyHomework(nameHomework, annotationHomework, calification);
	}

	public void assignStudentCourse(String codeStudent, String nameCourse, String nameTeacher) throws Exception {
		if (isExistCourse(nameCourse)) {
			for (int i = 0; i < courseGeneralList.size(); i++) {
				if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
						&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
					String description = courseGeneralList.get(i).getDescriptionActivity();
					String schedule = courseGeneralList.get(i).getScheduleActivity();
					getStudent(codeStudent).addCourse(new Course(nameCourse, nameTeacher, description, schedule));
				}
			}
		} else {
			throw new Exception();
		}
	}

	private boolean isExistCourse(String nameCourse) {
		boolean isExist = false;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
				isExist = true;
			}
		}
		return isExist;
	}

	public void cancelStudentCourse(String codeStudent, String nameCourse) throws Exception {
		getStudent(codeStudent).cancelCourse(nameCourse);
	}

	public void cancelStudentHomework(String codeStudent, String nameCourse, String nameHomework) throws Exception {
		getStudent(codeStudent).cancelHomework(nameCourse, nameHomework);
	}

	public void addStudentExternalActivity(String codeStudent, String nameExActivity, String descriptionExActivity,
			String scheduleExActivity) throws Exception {
		getStudent(codeStudent)
				.addExternalActivity(new ExternalActivity(nameExActivity, descriptionExActivity, scheduleExActivity));
	}

	public String getStudentExternalActivities(String codeStudent) throws Exception {
		String externalActivities = ConstantsModels.EMPTY_STRING;
		ArrayList<ExternalActivity> externalActivitiesList = getStudent(codeStudent).getExternalActivityList();
		for (int i = 0; i < externalActivitiesList.size(); i++) {
			externalActivities += externalActivitiesList.get(i).getNameActivity()
					+ ConstantsModels.SEPARATOR_THREE_DOT_AND_COMA;
		}
		return externalActivities;
	}

	public String getStudentTotalExternalActivities(String codeStudent) throws Exception {
		String externalActivities = ConstantsModels.EMPTY_STRING;
		ArrayList<ExternalActivity> externalActivitiesList = getStudent(codeStudent).getExternalActivityList();
		for (int i = 0; i < externalActivitiesList.size(); i++) {
			externalActivities += externalActivitiesList.get(i).toString() + ConstantsModels.SEPARATOR_DOT_AND_COMA;
		}
		return externalActivities;
	}

	public String getStudentSpecificExternalActivity(String codeStudent, String nameExActivity) throws Exception {
		return getStudent(codeStudent).getExternalActivity(nameExActivity).toString();
	}

	public void modifyExternalActivity(String codeStudent, String nameExActivity, String descriptionExActivity,
			String schedulerExActivity) throws Exception {
		getStudent(codeStudent).modifyExternalActivity(nameExActivity, descriptionExActivity, schedulerExActivity);
	}

	public void cancelExternalActivity(String codeStudent, String nameExActivity) throws Exception {
		getStudent(codeStudent).cancelExternalActivity(nameExActivity);
	}

	public double calculateAvgCourseCalification(String codeStudent, String nameCourse) throws Exception {
		return getStudent(codeStudent).calculateAvgCourseCalification(nameCourse);
	}

	public double calculateTotalAvgCalification(String codeStudent) throws Exception {
		return getStudent(codeStudent).calculateTotalAvgCalification();
	}

	public void assignCourseTeacher(String nameTeacher, String nameCourse, String descriptionCourse,
			String schedulerCourse) throws Exception {
		boolean exist = false;
		String[] schedules = schedulerCourse.split("  ->  ");
		String formatSchedules = formaterSchedule(schedules);
		
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				exist = true;
			}
		}
		if (!exist) {
			courseGeneralList.add(new Course(nameCourse, nameTeacher, descriptionCourse, formatSchedules));
			availableCourses.insert(nameCourse);
		}else {
			throw new Exception();
		}
	}

	private String formaterSchedule(String[] schedules) {
		String formatSchedules = "";
		for (String string : schedules) {
			string = string.replace(':', '#');
			string = string.replace('-', '#');
			formatSchedules += string + "%";
		}
		return formatSchedules;
	}

	public void cancelTeacherCourse(String nameTeacher, String nameCourse) throws Exception {
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				courseGeneralList.remove(courseGeneralList.get(i));
				if (isUniqueCourseTeacher(nameCourse)) {
					if (!availableCourses.isEmpty()) {
						availableCourses.delete(nameCourse);
					}else {
						throw new Exception();
					}
				}
			}
		}

		Iterator<Student> it = studentTree.inOrder();
		while (it.hasNext()) {
			Student student = (Student) it.next();
			ArrayList<Course> list = student.getCourseList();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
						&& list.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
					student.getCourseList().remove(i);
				}
			}
		}
	}

	private boolean isUniqueCourseTeacher(String nameCourse) {
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse) && !courseGeneralList.get(i)
					.getNameCourseTeacher().equalsIgnoreCase(ConstantsModels.EMPTY_STRING)) {
				return false;
			}
		}
		return true;
	}

	public String getSpecificCourseTeacher(String nameCourse, String nameTeacher) {
		String specificCourseTeacher = ConstantsModels.EMPTY_STRING;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				specificCourseTeacher = courseGeneralList.get(i).toString();
			}
		}
		return specificCourseTeacher;
	}

	public void modifyCourseTeacher(String nameTeacher, String nameCourse, String descriptionCourse,
			String schedulerCourse) {
		String[] schedules = schedulerCourse.split("  ->  ");
		String formatSchedules = formaterSchedule(schedules);
		for (int i = 0; i < courseGeneralList.size(); i++) {			
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				courseGeneralList.get(i).setDescriptionActivity(descriptionCourse);
				courseGeneralList.get(i).setScheduleActivity(formatSchedules);
			}
		}
		Iterator<Student> it = studentTree.inOrder();
		while (it.hasNext()) {
			Student student = (Student) it.next();
			ArrayList<Course> list = student.getCourseList();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
						&& list.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
					student.getCourseList().get(i).setDescriptionActivity(descriptionCourse);
				}
			}
		}
	}

	public String getInfoSchedule(String nameCourse, String nameTeacher) {
		String schedule = ConstantsModels.EMPTY_STRING;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				schedule += courseGeneralList.get(i).getScheduleActivity();
			}
		}
		return schedule;
	}

	public ArrayList<Course> getCourseGeneralList() {
		return courseGeneralList;
	}

	private Comparator<String> stringComparator() {
		return new Comparator<String>() {
			public int compare(String stringOne, String stringTwo) {
				return stringOne.compareToIgnoreCase(stringTwo);
			}
		};
	}

	private Comparator<Student> studentComparator() {
		return new Comparator<Student>() {
			public int compare(Student studentOne, Student studentTwo) {
				int compare = studentOne.getCodeUser().compareToIgnoreCase(studentTwo.getCodeUser());
				if (compare < 0) {
					return 1;
				} else if (compare == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		};
	}

	private Comparator<Teacher> teacherComparator() {
		return new Comparator<Teacher>() {
			public int compare(Teacher teacherOne, Teacher teacherTwo) {
				int compare = teacherOne.getCodeUser().compareToIgnoreCase(teacherTwo.getCodeUser());
				if (compare < 0) {
					return 1;
				} else if (compare == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		};
	}

	private void loadDataJSon() {
		archiveClass = GSONFileManager.readFile();
		studentTree = new AVLTree<>(studentComparator());
		ArrayList<Student> students = archiveClass.getStudents();
		for (Student student : students) {
			studentTree.insert(student);
		}
		teacherTree = new AVLTree<>(teacherComparator());
		ArrayList<Teacher> teachers = archiveClass.getTeachers();
		for (Teacher teacher : teachers) {
			teacherTree.insert(teacher);
		}
		availableCourses = new AVLTree<>(stringComparator());
		ArrayList<String> courses = archiveClass.getAvailableCourses();
		for (String course : courses) {
			availableCourses.insert(course);
		}
		courseGeneralList = archiveClass.getCourseGeneralList();
	}

	public AVLTree<Student> getStudentTree() {
		return studentTree;
	}

	public AVLTree<Teacher> getTeacherTree() {
		return teacherTree;
	}

	public AVLTree<String> getAvailableCourse() {
		return availableCourses;
	}
}