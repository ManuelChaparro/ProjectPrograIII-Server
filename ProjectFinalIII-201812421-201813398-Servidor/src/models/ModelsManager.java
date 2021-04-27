package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import persistence.GSONFileManager;
import structures.AVLTree;

public class ModelsManager {

	private AVLTree<Student> studentTree;
	private AVLTree<Teacher> teacherTree;
	private ArrayList<Course> courseGeneralList;
	private GSONFileManager gsonManager;

	public ModelsManager() {
		studentTree = new AVLTree<Student>(studentComparator());
		teacherTree = new AVLTree<Teacher>(teacherComparator());
		courseGeneralList = new ArrayList<Course>();
		gsonManager = new GSONFileManager();
	}

	public void createStudent(Student student) throws Exception {
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
		if (!courseGeneralList.contains(course)) {
			courseGeneralList.add(course);
		} else {
			throw new Exception("La asignatura " + course.getNameActivity() + " ya existe.");
		}
	}

	public Course getGeneralCourse(String nameCourse) throws Exception {
		if (courseGeneralList.contains(new Course(nameCourse))) {
			for (int i = 0; i < courseGeneralList.size(); i++) {
				if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse)) {
					return courseGeneralList.get(i);
				}
			}
		} else {
			throw new Exception("La asignatura " + nameCourse + " no existe.");
		}
		return null;
	}

//  nuevo para asignar al estudiante la respectiva asignatura
	public void assignStudentCourse(String codeStudent, String nameCourse, String nameTeacher) throws Exception {
		if (courseGeneralList.contains(new Course(nameCourse))) {
			for (int j = 0; j < courseGeneralList.size(); j++) {
				if (courseGeneralList.get(j).getNameActivity().equalsIgnoreCase(nameCourse)
						&& courseGeneralList.get(j).getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
					getStudent(codeStudent).addCourse(courseGeneralList.get(j));
				}
			}
		} else {
			throw new Exception("La asignatura que desea inscribir no existe.");
		}
	}

//	metodo nuevO para el caso de que el estudiante quiera cancelar la materia de
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
		return getStudent(codeStudent).calculateAvgCourseCalification(nameCourse);
	}

	// Metodo para calcular el promedio general de un estudiante.
	public double calculateTotalAvgCalification(String codeStudent) throws Exception {
		return getStudent(codeStudent).calculateTotalAvgCalification();
	}

	// Metodo para que el docente pueda seleccionar la asignatura y pueda agregar
	// descripcion y horario.
	public void assignCourseTeacher(String codeTeacher, String nameCourse, String descriptionCourse,
			String schedulerCourse) throws Exception {
		if (courseGeneralList.contains(new Course(nameCourse))) {
			for (int i = 0; i < courseGeneralList.size(); i++) {
				if (courseGeneralList.get(i).getNameActivity().equalsIgnoreCase(nameCourse) && !courseGeneralList.get(i)
						.getNameCourseTeacher().equalsIgnoreCase(getTeacher(codeTeacher).getNameUser())) {
					courseGeneralList.add(
							new Course(courseGeneralList.get(i).getNameActivity(), descriptionCourse, schedulerCourse));
				}
			}
		} else {
			throw new Exception("La asignatura que desea inscribir no existe.");
		}
	}
	
	public boolean isExistStudent(String codeStudent) {
		return studentTree.isIntoTree(new Student(codeStudent));
	}

	public ArrayList<Course> getCourseGeneralList() {
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
}