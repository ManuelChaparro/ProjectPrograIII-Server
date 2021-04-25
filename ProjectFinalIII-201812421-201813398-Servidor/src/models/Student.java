package models;

import java.util.Comparator;

import structures.AVLTree;

public class Student extends User {

	private AVLTree<Course> courseTree;
	private AVLTree<ExternalActivity> externalActivitiesTree;

	public Student(String nameStudent, String codeStudent, String password) {
		super(nameStudent, codeStudent, password);
		courseTree = new AVLTree<Course>(courseComparator());
		externalActivitiesTree = new AVLTree<ExternalActivity>(exActivityComparator());
		courseTree.createTree();
		externalActivitiesTree.createTree();
	}

	public Student(String codeStudent) {
		super(codeStudent);
	}

	public void addCourse(Course course) throws Exception {
		if (!courseTree.isIntoTree(course)) {
			courseTree.insert(course);
		} else {
			throw new Exception("La asignatura que desea inscribir ya existe.");
		}
	}
	
	public void cancelCourse(String nameCourse) throws Exception {
		Course course = new Course(nameCourse);
		if (courseTree.isIntoTree(course)) {
			courseTree.deleteNode(course);
		}else {
			throw new Exception("La asignatura que desea eliminar, no ha sido inscrita.");
		}
	}

	public Course getCourse(String nameCourse) throws Exception {
		Course course = new Course(nameCourse);
		if (courseTree.isIntoTree(course)) {
			return courseTree.findNode(course).getData();
		} else {
			throw new Exception("La asignatura que usted busca no existe.");
		}
	}

	public AVLTree<Course> getCourseList() {
		return courseTree;
	}

	public void addExternalActivity(ExternalActivity externalActivity) throws Exception {
		if (!externalActivitiesTree.isIntoTree(externalActivity)) {
			externalActivitiesTree.insert(externalActivity);
		} else {
			throw new Exception("La actividad externa que desea añadir ya existe.");
		}
	}

	public ExternalActivity getExternalActivity(String nameExActivity) throws Exception {
		ExternalActivity exActivity = new ExternalActivity(nameExActivity);
		if (externalActivitiesTree.isIntoTree(exActivity)) {
			return externalActivitiesTree.findNode(exActivity).getData();
		} else {
			throw new Exception("La actividad que busca no existe.");
		}
	}

	public AVLTree<ExternalActivity> getExternalActivityList() {
		return externalActivitiesTree;
	}

	private Comparator<Course> courseComparator() {
		return new Comparator<Course>() {
			@Override
			public int compare(Course courseOne, Course courseTwo) {
				int compare = courseOne.getNameActivity().compareToIgnoreCase(courseTwo.getNameActivity());
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

	private Comparator<ExternalActivity> exActivityComparator() {
		return new Comparator<ExternalActivity>() {
			@Override
			public int compare(ExternalActivity exActOne, ExternalActivity exActTwo) {
				int compare = exActOne.getNameActivity().compareToIgnoreCase(exActTwo.getNameActivity());
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

	public String toString() {
		return "Student [getNameUser()=" + getNameUser() + ", getCode()=" + getCodeUser() + ", getPassword()="
				+ getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}
