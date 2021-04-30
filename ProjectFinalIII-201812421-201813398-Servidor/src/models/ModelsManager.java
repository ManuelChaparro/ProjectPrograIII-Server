package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import persistence.ArchiveClass;
import persistence.GSONFileManager;
import structures.AVLtree;

public class ModelsManager {

	private ArchiveClass archiveClass;
	private AVLtree<Student> studentTree;
	private AVLtree<Teacher> teacherTree;
	private AVLtree<String> availableCourses;
	private ArrayList<Course> courseGeneralList;

	public ModelsManager() {
		loadData();
	}

	public void createStudent(Student student) throws Exception {
		if (!studentTree.exist(student)) {
			studentTree.insert(student);
		} else {
			throw new Exception("El estudiante " + student.getNameUser() + " ya existe.");
		}
	}

	public boolean isExistStudent(String codeStudent, String password) {
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

	public void createTeacher(Teacher teacher) throws Exception {
		if (!teacherTree.exist(teacher)) {
			teacherTree.insert(teacher);
		} else {
			throw new Exception("El docente " + teacher.getNameUser() + " ya existe.");
		}
	}

	private Student getStudent(String codeStudent) throws Exception {
		Student student = new Student(codeStudent);
		if (studentTree.exist(student)) {
			return studentTree.find(student);
		} else {
			throw new Exception("El estudiante que busca no existe.");
		}
	}

	private Teacher getTeacher(String codeTeacher) throws Exception {
		Teacher teacher = new Teacher(codeTeacher);
		if (teacherTree.exist(teacher)) {
			return teacherTree.find(teacher);
		} else {
			throw new Exception("El profesor que busca no existe.");
		}
	}

	// metodo unicamente para crear y anadir asignatura a la lista general de
	// asignaturas.
	public void addCourseGeneralList(Course course) throws Exception {
		if (!courseGeneralList.contains(course)) {
			courseGeneralList.add(course);
		} else {
			throw new Exception("La asignatura " + course.getNameActivity() + " ya existe.");
		}
	}

	public String getAvailableCourses() throws Exception {
		String courses = "";
		Iterator<String> itAvailableCourses = availableCourses.inOrder();
		while (itAvailableCourses.hasNext()) {
			courses += itAvailableCourses.next() + ";";

		}
		return courses;
	}

	public String getAvailableTeachers(String course) {
		String teachers = "";
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(course)
					&& !courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase("")) {
				teachers += courseGeneralList.get(i).getNameCourseTeacher() + ";";
			}
		}
		return teachers;
	}

//----------Metodos para anadir y modificar tareas --------------------------------
	public void addStudentHomework(String codeStudent, String nameCourse, String nameHomework,
			String annotationHomework, double calification) throws Exception {
		if (!nameHomework.equalsIgnoreCase("")) {
			getStudent(codeStudent).addHomework(nameCourse, nameHomework, annotationHomework, calification);
		} else {
			throw new Exception();
		}
	}

	public String getStudentCourses(String codeStudent) throws Exception {
		String courses = "";
		ArrayList<Course> coursesList = getStudent(codeStudent).getCourseList();
		for (int i = 0; i < coursesList.size(); i++) {
			courses += coursesList.get(i).getNameActivity() + ";";
		}
		return courses;
	}

	public String getStudentHomeworks(String codeStudent, String nameCourse) throws Exception {
		String homeworks = "";
		ArrayList<Homework> homeworksList = getStudent(codeStudent).getCourse(nameCourse).getHomeworkList();
		for (int i = 0; i < homeworksList.size(); i++) {
			homeworks += homeworksList.get(i).getNameHomework() + ";";
		}
		return homeworks;
	}

	public String getSpecificStudentHomework(String codeStudent, String nameCourse, String nameHomework)
			throws Exception {
		return getStudent(codeStudent).getCourse(nameCourse).getHomework(nameHomework).toString();
	}

	public void modifySpecificHomework(String codeStudent, String nameCourse, String nameHomework,
			String annotationHomework, double calification) throws Exception {
		getStudent(codeStudent).getCourse(nameCourse).modifyHomework(nameHomework, nameHomework, calification);
	}
	// --------------------------------------------------------

	public void assignStudentCourse(String codeStudent, String nameCourse, String nameTeacher) throws Exception {
		if (isExistCourse(nameCourse)) {
			for (int i = 0; i < courseGeneralList.size(); i++) {
				if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
						&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
					getStudent(codeStudent).addCourse(courseGeneralList.get(i));
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

//-----------Metodos para cancelar tarea/ asignatura
	public void cancelStudentCourse(String codeStudent, String nameCourse) throws Exception {
		getStudent(codeStudent).cancelCourse(nameCourse);
	}

	public void cancelStudentHomework(String codeStudent, String nameCourse, String nameHomework) throws Exception {
		getStudent(codeStudent).cancelHomework(nameCourse, nameHomework);
	}

//--------------------------------------------------

//-------------Metodos para Agregar / modificar actividad externa
	public void addStudentExternalActivity(String codeStudent, String nameExActivity, String descriptionExActivity,
			String scheduleExActivity) throws Exception {
		getStudent(codeStudent)
				.addExternalActivity(new ExternalActivity(nameExActivity, descriptionExActivity, scheduleExActivity));
	}

	public String getStudentExternalActivities(String codeStudent) throws Exception {
		String externalActivities = "";
		ArrayList<ExternalActivity> externalActivitiesList = getStudent(codeStudent).getExternalActivityList();
		for (int i = 0; i < externalActivitiesList.size(); i++) {
			externalActivities += externalActivitiesList.get(i).getNameActivity() + ";";
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

//---------------------------------------------------------------

//----------------Metodo para cancelar actividad-----------------
	public void cancelExternalActivity(String codeStudent, String nameExActivity) throws Exception {
		getStudent(codeStudent).cancelExternalActivity(nameExActivity);
	}

//---------------------------------------------------------------

//	// Metodo para calcular el promedio de notas para la asignatura que se quiera.
	public double calculateAvgCourseCalification(String codeStudent, String nameCourse) throws Exception {
		return getStudent(codeStudent).calculateAvgCourseCalification(nameCourse);
	}

//
//	// Metodo para calcular el promedio general de un estudiante.
	public double calculateTotalAvgCalification(String codeStudent) throws Exception {
		return getStudent(codeStudent).calculateTotalAvgCalification();
	}

//----------------------Metodo para asignar la materia al profesor
	public void assignCourseTeacher(String nameTeacher, String nameCourse, String descriptionCourse,
			String schedulerCourse) throws Exception {
		boolean exist = false;
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& !courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				exist = true;
			}
		}
		if (!exist) {
			courseGeneralList.add(new Course(nameCourse, nameTeacher, descriptionCourse, schedulerCourse));
			availableCourses.insert(nameCourse);
		}
	}

//------------------------------------------------------------------

//------------------------Metodo para que el profesor pueda cancelar la asignatura
	public void cancelTeacherCourse(String nameTeacher, String nameCourse) {
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				courseGeneralList.remove(courseGeneralList.get(i));
				if (!isUniqueCourseTeacher(nameCourse)) {
					availableCourses.delete(nameCourse);
				}
			}
		}
	}

	private boolean isUniqueCourseTeacher(String nameCourse) {
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& !courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase("")) {
				return false;
			}
		}
		return true;
	}
//-------------------------------------------------------------------------------

//------------------Metodo para modificar el curso del profesor
	public String getSpecificCourseTeacher(String nameCourse, String nameTeacher) {
		String specificCourseTeacher = "";
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				specificCourseTeacher = courseGeneralList.get(i).toString();
			}
		}
		return specificCourseTeacher;
	}

	public void modifyCourseTeacher(String nameCourse, String nameTeacher, String descriptionCourse,
			String schedulerCourse) {
		for (int i = 0; i < courseGeneralList.size(); i++) {
			if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)
					&& courseGeneralList.get(i).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				courseGeneralList.get(i).setDescriptionActivity(descriptionCourse);
				courseGeneralList.get(i).setScheduleActivity(schedulerCourse);
			}
		}
	}

//----------------------------------------------------------------
	public String getInfoSchedule(String nameCourse, String nameTeacher) {
		String schedule = "";
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

	private void loadData() {
		archiveClass = GSONFileManager.readFile();
		studentTree = new AVLtree<>(studentComparator());
		ArrayList<Student> students = archiveClass.getStudents();
		for (Student student : students) {
			studentTree.insert(student);
		}
		teacherTree = new AVLtree<>(teacherComparator());
		ArrayList<Teacher> teachers = archiveClass.getTeachers();
		for (Teacher teacher : teachers) {
			teacherTree.insert(teacher);
		}
		availableCourses = new AVLtree<>(stringComparator());
		ArrayList<String> courses = archiveClass.getAvailableCourses();
		for (String course : courses) {
			availableCourses.insert(course);
		}
		courseGeneralList = archiveClass.getCourseGeneralList();
	}

	public AVLtree<Student> getStudentTree() {
		return studentTree;
	}

	public AVLtree<Teacher> getTeacherTree() {
		return teacherTree;
	}

	public AVLtree<String> getAvailableCourse() {
		return availableCourses;
	}
}