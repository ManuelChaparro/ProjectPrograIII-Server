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
	}

	public void createStudent(String nameStudent, String code, String password) throws Exception {
		Student student = new Student(nameStudent, code, password);
		if (!studentTree.isIntoTree(student)) {
			studentTree.insert(student);
		} else {
			throw new Exception("El estudiante " + student.getNameUser() + " ya existe.");
		}
	}

	public void createTeacher(String nameTeacher, String code, String password) throws Exception {
		Teacher teacher = new Teacher(nameTeacher, code, password);
		if (!teacherTree.isIntoTree(teacher)) {
			teacherTree.insert(teacher);
		} else {
			throw new Exception("El docente " + teacher.getNameUser() + " ya existe.");
		}
	}

	public Student getStudent(String code) throws Exception {
		Student student = new Student(code);
		if (studentTree.isIntoTree(student)) {
			return studentTree.findNode(student).getData();
		} else {
			throw new Exception("El estudiante que busca no existe.");
		}
	}

	public Teacher getTeacher(String code) throws Exception {
		Teacher teacher = new Teacher(code);
		if (teacherTree.isIntoTree(teacher)) {
			return teacherTree.findNode(teacher).getData();
		} else {
			throw new Exception("El profesor que busca no existe.");
		}
	}

	// metodo unicamente para crear y añadir asignatura a la lista general de
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
	public void assignStudentCourse(String codeUser, String nameCourse) throws Exception {
		Iterator<Course> itCourse = courseGeneralList.iterator();
		Student student = new Student(codeUser);
		if (courseGeneralList.exist(new Course(nameCourse))) {
			while (itCourse.hasNext()) {
				if (itCourse.next().getNameActivity().equalsIgnoreCase(nameCourse)) {
					studentTree.findNode(student).getData().addCourse(itCourse.next());
				}
			}
		} else {
			throw new Exception("La asignatura que desea inscribir no existe.");
		}
	}

	// aca pasamos el codigo del estudiante para poder buscarlo y si es el caso
	// asignarle una actividad externa.
	public void addExternalActivity(String codeUser, String nameExActivity, String descriptionExActivity,
			String scheduleExActivity) throws Exception {
		Iterator<Student> itStudent = studentTree.inOrder();
		Student student = new Student(codeUser);
		if (studentTree.isIntoTree(student)) {
			ExternalActivity exActivity = new ExternalActivity(nameExActivity, descriptionExActivity,
					scheduleExActivity);
			while (itStudent.hasNext()) {
				if (itStudent.next().getCode().equalsIgnoreCase(codeUser)) {
					if (!itStudent.next().getExternalActivityList().isIntoTree(exActivity)) {
						itStudent.next().addExternalActivity(exActivity);
					}
				}
			}
		} else {
			throw new Exception("No existe estudiante con codigo: " + codeUser);
		}
	}

	// aca pasamos el codigo del estudiante para poder buscarlo y si es el caso
	// obtener una actividad externa especifica.
	public ExternalActivity getExternalActivity(String codeUser, String nameExActivity) throws Exception {
		Iterator<Student> itStudent = studentTree.inOrder();
		while (itStudent.hasNext()) {
			if (itStudent.next().getCode().equalsIgnoreCase(codeUser)) {
				return itStudent.next().getExternalActivity(nameExActivity);
			}
		}
		throw new Exception("No existe un estudiante con codigo: " + codeUser);
	}

	// Metodo para calcular el promedio de notas para la asignatura que se quiera.
	public double calculateAvgCourseCalification(String codeUser, String nameCourse) throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCalification = 0;
		Iterator<Student> itStudent = studentTree.inOrder();
		if (studentTree.isIntoTree(new Student(codeUser))) {
			while (itStudent.hasNext()) {
				if (itStudent.next().getCode().equalsIgnoreCase(codeUser)) {
					Iterator<Course> itCourse = itStudent.next().getCourseList().inOrder();
					while (itCourse.hasNext()) {
						if (itCourse.next().getNameActivity().equalsIgnoreCase(nameCourse)) {
							Iterator<Homework> itHomework = itCourse.next().getHomeworkList().iterator();
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
			throw new Exception("No existe estudiante con codigo: " + codeUser);
		}
		return result;
	}

	// Metodo para calcular el promedio general de un estudiante.
	public double calculateTotalAvgCalification(String codeUser) throws Exception {
		double result = 0;
		double sumatoryCalification = 0;
		int quantityCourses = 0;
		Iterator<Student> itStudent = studentTree.inOrder();
		if (studentTree.isIntoTree(new Student(codeUser))) {
			while (itStudent.hasNext()) {
				if (itStudent.next().getCode().equalsIgnoreCase(codeUser)) {
					Iterator<Course> itCourse = itStudent.next().getCourseList().inOrder();
					while (itCourse.hasNext()) {
						quantityCourses++;
						sumatoryCalification += calculateAvgCourseCalification(codeUser,
								itCourse.next().getNameActivity());
					}
					result = sumatoryCalification / quantityCourses;
					return result;
				}
			}
		} else {
			throw new Exception("No existe estudiante con codigo: " + codeUser);
		}
		return result;
	}

	public DoubleList<Course> getCourseGeneralList() {
		return courseGeneralList;
	}

	public void manageCourseAdmin() {

	}

	private Comparator<Student> studentComparator() {
		return new Comparator<Student>() {

			@Override
			public int compare(Student studentOne, Student studentTwo) {
				int compare = studentOne.getCode().compareToIgnoreCase(studentTwo.getCode());
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

			@Override
			public int compare(Teacher teacherOne, Teacher teacherTwo) {
				int compare = teacherOne.getCode().compareToIgnoreCase(teacherTwo.getCode());
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

			@Override
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