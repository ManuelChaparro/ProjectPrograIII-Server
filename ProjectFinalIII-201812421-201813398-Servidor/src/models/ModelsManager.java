package models;

import java.util.Comparator;
import java.util.Iterator;

import persistence.GSONFileManager;
import structures.AVLTree;
import structures.DoubleList;

public class ModelsManager {

	private AVLTree<Student> studentTree;
	private AVLTree<Teacher> teacherTree;
	private DoubleList<Course> courseGeneralList;
	private GSONFileManager gsonManager;

	public ModelsManager() {
		studentTree = new AVLTree<Student>(studentComparator());
		teacherTree = new AVLTree<Teacher>(teacherComparator());
		courseGeneralList = new DoubleList<Course>(courseGeneralComparator());
		gsonManager = new GSONFileManager();
	}

	public void createStudent(String nameStudent, String codeStudent, String password) throws Exception {
		Student student = new Student(nameStudent, codeStudent, password);
		if (!studentTree.isIntoTree(student)) {
			studentTree.insert(student);
		} else {
			throw new Exception("El estudiante " + student.getNameUser() + " ya existe.");
		}
	}

	public void createTeacher(String nameTeacher, String codeTeacher, String password) throws Exception {
		Teacher teacher = new Teacher(nameTeacher, codeTeacher, password);
		if (!teacherTree.isIntoTree(teacher)) {
			teacherTree.insert(teacher);
		} else {
			throw new Exception("El docente " + teacher.getNameUser() + " ya existe.");
		}
	}

	public Student getStudent(String codeStudent) throws Exception {
		Student student = new Student(codeStudent);
		if (studentTree.isIntoTree(student)) {
			return studentTree.findNode(student).getData();
		} else {
			throw new Exception("El estudiante que busca no existe.");
		}
	}

	public Teacher getTeacher(String codeTeacher) throws Exception {
		Teacher teacher = new Teacher(codeTeacher);
		if (teacherTree.isIntoTree(teacher)) {
			return teacherTree.findNode(teacher).getData();
		} else {
			throw new Exception("El profesor que busca no existe.");
		}
	}

	// metodo unicamente para crear y a�adir asignatura a la lista general de
	// asignaturas.
	public void addCourseGeneralList(Course course) throws Exception {
		if (!courseGeneralList.exist(course)) {
			courseGeneralList.insert(course);
		} else {
			throw new Exception("La asignatura " + course.getNameActivity() + " ya existe.");
		}
	}

	public Course getGeneralCourse(String nameCourse) throws Exception {
		if (courseGeneralList.exist(new Course(nameCourse))) {
			Iterator<Course> itCourse = courseGeneralList.iterator();
			while (itCourse.hasNext()) {
				if (itCourse.next().getNameActivity().equalsIgnoreCase(nameCourse)) {
					return itCourse.next();
				}
			}
		} else {
			throw new Exception("La asignatura " + nameCourse + " no existe.");
		}
		return null;
	}

	// metodo nuevo para asignar al estudiante la respectiva asignatura
	public void assignStudentCourse(String codeStudent, String nameCourse, String nameTeacher) throws Exception {
		Iterator<Course> itCourse = courseGeneralList.iterator();
		Student student = new Student(codeStudent);
		if (courseGeneralList.exist(new Course(nameCourse))) {
			while (itCourse.hasNext()) {
				Course course = itCourse.next();
				if (course.getNameActivity().equalsIgnoreCase(nameCourse)
						&& course.getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
					studentTree.findNode(student).getData().addCourse(course);
				}
			}
		} else {
			throw new Exception("La asignatura que desea inscribir no existe.");
		}
	}
	
	//metodo nuev para el caso de que el estudiante quiera cancelar la materia de su horario.
	public void cancelStudentCourse(String codeStudent, String nameCourse) throws Exception {
		Student student = new Student(codeStudent);
		if(studentTree.isIntoTree(student)) {
			studentTree.findNode(student).getData().cancelCourse(nameCourse);
		}else {
			throw new Exception("El estudiante no existe.");
		}
	}

	// aca pasamos el codigo del estudiante para poder buscarlo y si es el caso
	// asignarle una actividad externa.
	public void addExternalActivity(String codeStudent, String nameExActivity, String descriptionExActivity,
			String scheduleExActivity) throws Exception {
		Iterator<Student> itStudent = studentTree.inOrder();
		Student student = new Student(codeStudent);
		if (studentTree.isIntoTree(student)) {
			ExternalActivity exActivity = new ExternalActivity(nameExActivity, descriptionExActivity,
					scheduleExActivity);
			while (itStudent.hasNext()) {
				Student nextStudent = itStudent.next();
				if (nextStudent.getCodeUser().equalsIgnoreCase(codeStudent)) {
					if (!nextStudent.getExternalActivityList().isIntoTree(exActivity)) {
						nextStudent.addExternalActivity(exActivity);
					}
				}
			}
		} else {
			throw new Exception("No existe estudiante con codigo: " + codeStudent);
		}
	}

	// aca pasamos el codigo del estudiante para poder buscarlo y si es el caso
	// obtener una actividad externa especifica.
	public ExternalActivity getExternalActivity(String codeStudent, String nameExActivity) throws Exception {
		Iterator<Student> itStudent = studentTree.inOrder();
		while (itStudent.hasNext()) {
			if (itStudent.next().getCodeUser().equalsIgnoreCase(codeStudent)) {
				return itStudent.next().getExternalActivity(nameExActivity);
			}
		}
		throw new Exception("No existe un estudiante con codigo: " + codeStudent);
	}

	// Metodo para calcular el promedio de notas para la asignatura que se quiera.
	public double calculateAvgCourseCalification(String codeStudent, String nameCourse) throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCalification = 0;
		Iterator<Student> itStudent = studentTree.inOrder();
		if (studentTree.isIntoTree(new Student(codeStudent))) {
			while (itStudent.hasNext()) {
				Student newStudent = itStudent.next();
				if (newStudent.getCodeUser().equalsIgnoreCase(codeStudent)) {
					Iterator<Course> itCourse = newStudent.getCourseList().inOrder();
					while (itCourse.hasNext()) {
						Course newCourse = itCourse.next();
						if (newCourse.getNameActivity().equalsIgnoreCase(nameCourse)) {
							Iterator<Homework> itHomework = newCourse.getHomeworkList().iterator();
							while (itHomework.hasNext()) {
								quantityCalification++;
								sumatoryCalification += itHomework.next().getCalification();
							}
							result = sumatoryCalification / quantityCalification;
							return result;
						}
					}
				}
			}
		} else {
			throw new Exception("No existe estudiante con codigo: " + codeStudent);
		}
		return result;
	}

	// Metodo para calcular el promedio general de un estudiante.
	public double calculateTotalAvgCalification(String codeStudent) throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCourses = 0;
		Iterator<Student> itStudent = studentTree.inOrder();
		if (studentTree.isIntoTree(new Student(codeStudent))) {
			while (itStudent.hasNext()) {
				Student actualStudent = itStudent.next();
				if (actualStudent.getCodeUser().equalsIgnoreCase(codeStudent)) {
					Iterator<Course> itCourse = actualStudent.getCourseList().inOrder();
					while (itCourse.hasNext()) {
						quantityCourses++;
						sumatoryCalification += calculateAvgCourseCalification(codeStudent,
								itCourse.next().getNameActivity());
					}
					result = sumatoryCalification / quantityCourses;
					return result;
				}
			}
		} else {
			throw new Exception("No existe estudiante con codigo: " + codeStudent);
		}
		return result;
	}

	public void assignCourseTeacher(String codeTeacher, String nameCourse, String descriptionCourse,
			String schedulerCourse) throws Exception {
		Iterator<Course> itCourse = courseGeneralList.iterator();
		if (courseGeneralList.exist(new Course(nameCourse))) {
			while (itCourse.hasNext()) {
				Course course = itCourse.next();
				if (course.getNameActivity().equalsIgnoreCase(nameCourse)
						&& !course.getNameCourseTeacher().equalsIgnoreCase(getTeacher(codeTeacher).getNameUser())) {
					courseGeneralList.insert(new Course(course.getNameActivity(), descriptionCourse, schedulerCourse));
				}
			}
		} else {
			throw new Exception("La asignatura que desea inscribir no existe.");
		}
	}

	public DoubleList<Course> getCourseGeneralList() {
		return courseGeneralList;
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

	private Comparator<Course> courseGeneralComparator() {
		return new Comparator<Course>() {
			public int compare(Course courseOne, Course courseTwo) {
				int compare = courseOne.getNameActivity().compareToIgnoreCase(courseTwo.getNameActivity());
				if (compare == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		};
	}

}