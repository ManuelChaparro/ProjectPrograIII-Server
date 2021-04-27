package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import persistence.GSONFileManager;
import structures.AVLTree;

public class ModelsManager {

	private AVLTree<Student> studentTree;
	private AVLTree<Teacher> teacherTree;
	private AVLTree<String>	availableCourses;
	private ArrayList<Course> courseGeneralList;
	private GSONFileManager gsonManager;

	public ModelsManager() {
		studentTree = new AVLTree<Student>(studentComparator());
		teacherTree = new AVLTree<Teacher>(teacherComparator());

		availableCourses = new AVLTree<String>(stringComparator());
		availableCourses.insert("Hola");
		availableCourses.insert("Manuel");
		availableCourses.insert("Santiago");
		courseGeneralList = new ArrayList<Course>();
		loadDefaulData();
		gsonManager = new GSONFileManager();
		
	}

	public void createStudent(Student student) throws Exception {
		if (!studentTree.isIntoTree(student)) {
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
		return studentTree.isIntoTree(new Student(codeStudent));
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
		for (Course course : courseGeneralList) {
			if (!course.getNameCourseTeacher().equalsIgnoreCase("")) {
				courses+=course.toString();
			}
		}
		return courses;
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
	public void assignCourseTeacher(String nameTeacher, String nameCourse, String descriptionCourse,
			String schedulerCourse) throws Exception {
		boolean exist = false;
		for (Course course : courseGeneralList) {
			if (course.getNameActivity().equalsIgnoreCase(nameCourse) && course.getNameCourseTeacher().equalsIgnoreCase(nameTeacher)) {
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
				if (stringOne.compareToIgnoreCase(stringTwo) == 0) {
					return 1;
				}else {
					return 0;
				}
				
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

	private void loadDefaulData() {
		courseGeneralList.add(new Course("PROGRAMACION I"));
		courseGeneralList.add(new Course("PROGRAMACION II"));
		courseGeneralList.add(new Course("PROGRAMACION III"));
		courseGeneralList.add(new Course("CALCULO I"));
		courseGeneralList.add(new Course("CALCULO II"));
		courseGeneralList.add(new Course("CALCULO III"));
		courseGeneralList.add(new Course("CALCULO IV"));
		courseGeneralList.add(new Course("METODOS NUMERICOS"));
		courseGeneralList.add(new Course("INGLES I"));
		courseGeneralList.add(new Course("INGLES II"));
		courseGeneralList.add(new Course("INGLES III"));
		courseGeneralList.add(new Course("INGLES IV"));
		courseGeneralList.add(new Course("INGLES V"));
		courseGeneralList.add(new Course("INGLES VI"));
		courseGeneralList.add(new Course("DISEÑO GRAFICO"));
		courseGeneralList.add(new Course("FISICA I"));
		courseGeneralList.add(new Course("FISICA II"));
		courseGeneralList.add(new Course("FISICA III"));
		courseGeneralList.add(new Course("ADMINISTRACION"));
		courseGeneralList.add(new Course("BASES DE DATOS I"));
		courseGeneralList.add(new Course("BASES DE DATOS II"));
		courseGeneralList.add(new Course("CATEDRA UNIVERSITARIA"));
		courseGeneralList.add(new Course("ETICA"));
		courseGeneralList.add(new Course("MATEMATICAS DISCRETAS"));
		courseGeneralList.add(new Course("INGENIERIA DE REQUISITOS"));
		courseGeneralList.add(new Course("ELECTRONICA GENERAL"));
		courseGeneralList.add(new Course("INGENIERIA DE SOFTWARE I"));
		courseGeneralList.add(new Course("INGENIERIA DE SOFTWARE II"));
		courseGeneralList.add(new Course("COMUNICACIONES"));
		courseGeneralList.add(new Course("INVESTIGACION DE OPERACIONES"));
		courseGeneralList.add(new Course("SISTEMAS DISTRIBUIDOS"));
		courseGeneralList.add(new Course("LENGUAJES FORMALES"));
		courseGeneralList.add(new Course("TRANSMISION DE DATOS"));
		courseGeneralList.add(new Course("SISTEMAS OPERATIVOS"));
		courseGeneralList.add(new Course("INTELIGENCIA COMPUTACIONAL"));
		courseGeneralList.add(new Course("REDES DE DATOS"));
		courseGeneralList.add(new Course("ARQUITECTURA DE COMPUTADORES"));
		courseGeneralList.add(new Course("SIMULACION DE COMPUTADORAS"));
		courseGeneralList.add(new Course("AUDITORIA DE SISTEMAS"));
		courseGeneralList.add(new Course("GERENCIA INFORMATICA"));
		
		try {
			createTeacher("Hoyitos", "2345", "1");
			createTeacher("Omaira", "1234", "1");
			createTeacher("Alexander", "3456", "1");
			
			assignCourseTeacher("Hoyitos", "PROGRAMACION III", "Bienvenidas perras", "LUN#6#8%MIE#10#12");
			assignCourseTeacher("Omaria Galindo", "PROGRAMACION III", "Hola soy omaewa kawai senpai :v<3", "MAR#10#12%MIE#12#2");
			assignCourseTeacher("Alexander Sapoperro", "PROGRAMACION III", "OLA", "MAR#10#12%MIE#12#2");
			assignCourseTeacher("lademetodosxd", "CALCULO I", "OLA", "MAR#10#12%MIE#12#2");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<String> it = availableCourses.inOrder();
		while (it.hasNext()) {
			String string = (String) it.next();
			System.out.println(string);
		}
	}
}