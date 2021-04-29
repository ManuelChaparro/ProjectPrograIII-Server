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

	public void createTeacher(String nameTeacher, String codeTeacher, String password) throws Exception {
		Teacher teacher = new Teacher(nameTeacher, codeTeacher, password);
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

	// metodo unicamente para crear y aï¿½adir asignatura a la lista general de
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
		Student student = getStudent(codeStudent);
		for (Course course : student.getCourseList()) {
			courses += course.getNameActivity() + ";";
		}
		return courses;
	}

	public String getStudentHomeworks(String codeStudent, String nameCourse) throws Exception {
		String homeworks = "";
		Student student = getStudent(codeStudent);
		for (Course course : student.getCourseList()) {
			if (course.getNameActivity().equalsIgnoreCase(nameCourse)) {
				for (Homework homework : course.getHomeworkList()) {
					homeworks += homework.getNameHomework() + ";";
				}
			}
		}
		return homeworks;
	}

	public String getSpecificStudentHomework(String codeStudent, String nameCourse, String nameHomework)
			throws Exception {
		String completeHomework = "";
		Student student = getStudent(codeStudent);
		for (Course course : student.getCourseList()) {
			if (course.getNameActivity().equalsIgnoreCase(nameCourse)) {
				for (Homework homework : course.getHomeworkList()) {
					if (homework.getNameHomework().equalsIgnoreCase(nameHomework)) {
						completeHomework = homework.toString();
					}
				}
			}
		}
		return completeHomework;
	}

	public void modifySpecificHomework(String codeStudent, String nameCourse, String nameHomework,
			String annotationHomework, double calification) throws Exception {
		Student student = getStudent(codeStudent);
		if (student.getCodeUser().equalsIgnoreCase(codeStudent)) {
			for (Course course : student.getCourseList()) {
				if (course.getNameActivity().equalsIgnoreCase(nameCourse)) {
					course.modifyHomeworkAnnotation(nameHomework, annotationHomework);
					course.modifyHomeworkCalification(nameHomework, calification);
				}
			}
		}
	}
	// --------------------------------------------------------

	public void assignStudentCourse(String codeStudent, String nameCourse, String nameTeacher) throws Exception {
		if (isExistCourse(nameCourse)) {
			for (Course course : courseGeneralList) {
				if (course.getNameActivity().equalsIgnoreCase(nameCourse)
						&& course.getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
					getStudent(codeStudent).addCourse(course);
				}
			}
		} else {
			throw new Exception();
		}
	}

	private boolean isExistCourse(String nameCourse) {
		boolean isExist = false;
		for (Course course : courseGeneralList) {
			if (course.getNameActivity().equalsIgnoreCase(nameCourse)) {
				isExist = true;
			}
		}
		return isExist;
	}

	// metodo nuevO para el caso de que el estudiante quiera cancelar la materia de
//	su horario.
	public void cancelStudentCourse(String codeStudent, String nameCourse) throws Exception {
		getStudent(codeStudent).cancelCourse(nameCourse);
	}

	// aca pasamos el codigo del estudiante para poder buscarlo y si es el caso
	// asignarle una actividad externa.
	public void addExternalActivity(String codeStudent, String nameExActivity, String descriptionExActivity,
			String scheduleExActivity) throws Exception {
		ExternalActivity exActivity = new ExternalActivity(nameExActivity, descriptionExActivity, scheduleExActivity);
		getStudent(codeStudent).addExternalActivity(exActivity);
	}

	// aca pasamos el codigo del estudiante para poder buscarlo y si es el caso
	// obtener una actividad externa especifica.
//	public ExternalActivity getExternalActivity(String codeStudent, String nameExActivity) throws Exception {
//		Iterator<Student> itStudent = studentTree.inOrder();
//		while (itStudent.hasNext()) {
//			if (itStudent.next().getCodeUser().equalsIgnoreCase(codeStudent)) {
//				return itStudent.next().getExternalActivity(nameExActivity);
//			}
//		}
//		throw new Exception("No existe un estudiante con codigo: " + codeStudent);
//	}
//
//	// Metodo para calcular el promedio de notas para la asignatura que se quiera.
//	public double calculateAvgCourseCalification(String codeStudent, String nameCourse) throws Exception {
//		return getStudent(codeStudent).calculateAvgCourseCalification(nameCourse);
//	}
//
//	// Metodo para calcular el promedio general de un estudiante.
//	public double calculateTotalAvgCalification(String codeStudent) throws Exception {
//		return getStudent(codeStudent).calculateTotalAvgCalification();
//	}

	// Metodo para que el docente pueda seleccionar la asignatura y pueda agregar
	// descripcion y horario.
	public void assignCourseTeacher(String nameTeacher, String nameCourse, String descriptionCourse,
			String schedulerCourse) throws Exception {
		boolean exist = false;
		for (Course course : courseGeneralList) {
			if (course.getNameActivity().equalsIgnoreCase(nameCourse)
					&& course.getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
				exist = true;
			}
		}
		if (!exist) {
			courseGeneralList.add(new Course(nameCourse, nameTeacher, descriptionCourse, schedulerCourse));
			availableCourses.insert(nameCourse);
		}
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

	public AVLtree<Student> getStudentTree() {
		return studentTree;
	}

	public AVLtree<Teacher> getTeacherTree() {
		return teacherTree;
	}

	public AVLtree<String> getAvailableCourse() {
		return availableCourses;
	}

	public String getInfoSchedule(String course, String teacher) {
		String schedule = "";
		for (Course list : courseGeneralList) {
			if (list.getNameActivity().equalsIgnoreCase(course)
					&& list.getNameCourseTeacher().equalsIgnoreCase(teacher)) {
				schedule += list.getScheduleActivity();
			}
		}
		return schedule;
	}
}