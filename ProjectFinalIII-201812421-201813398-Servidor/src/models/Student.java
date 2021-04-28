package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import structures.AVLtree;

public class Student extends User {

	private ArrayList<Course> courseList;
	private ArrayList<ExternalActivity> externalActivitiesList;

	public Student(String nameStudent, String codeStudent, String password) {
		super(nameStudent, codeStudent, password);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
	}

	public Student(String codeStudent) {
		super(codeStudent);
		courseList = new ArrayList<Course>();
		externalActivitiesList = new ArrayList<ExternalActivity>();
	}

	public void addCourse(Course course) throws Exception {
		initArrayCourseList();
		boolean exist = false;
		for (int i = 0; i < courseList.size(); i++) {
			if (courseList.get(i).getNameActivity().equalsIgnoreCase(course.getNameActivity())) {
				exist = true;
			}
		}

		if (!exist) {
			courseList.add(course);
		} else {
			throw new Exception("La asignatura que desea inscribir ya existe.");
		}
	}

	private void initArrayCourseList() {
		if (courseList == null) {
			courseList = new ArrayList<Course>();
		}
	}

	public void cancelCourse(String nameCourse) throws Exception {
		for (Course auxCourse : courseList) {
			if (auxCourse.getNameActivity().equalsIgnoreCase(nameCourse)) {
				courseList.remove(auxCourse);
			}
		}
	}

	public Course getCourse(String nameCourse) throws Exception {
		Course course = null;
		for (Course auxCourse : courseList) {
			if (auxCourse.getNameActivity().equalsIgnoreCase(nameCourse)) {
				course = auxCourse;
			}
		}
		return course;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

//	public void addExternalActivity(ExternalActivity externalActivity) throws Exception {
//		if (!externalActivitiesTree.exist(externalActivity)) {
//			externalActivitiesTree.insert(externalActivity);
//		} else {
//			throw new Exception("La actividad externa que desea a�adir ya existe.");
//		}
//	}
//
//	public void modifyExternalActivityDescription(String nameExActivity, String descriptionExActivity)
//			throws Exception {
//		getExternalActivity(nameExActivity).setDescriptionActivity(descriptionExActivity);
//	}
//
//	public void deleteExternalActivity(String nameExActivity) throws Exception {
//		ExternalActivity exActivity = new ExternalActivity(nameExActivity);
//		if (externalActivitiesTree.exist(exActivity)) {
//			externalActivitiesTree.delete(exActivity);
//		} else {
//			throw new Exception("La actividad externa que desea eliminar no existe.");
//		}
//	}
//
//	public ExternalActivity getExternalActivity(String nameExActivity) throws Exception {
//		ExternalActivity exActivity = new ExternalActivity(nameExActivity);
//		if (externalActivitiesTree.exist(exActivity)) {
//			return externalActivitiesTree.find(exActivity);
//		} else {
//			throw new Exception("La actividad que busca no existe.");
//		}
//	}
//
//	public AVLtree<ExternalActivity> getExternalActivityList() {
//		return externalActivitiesTree;
//	}
//
//	public double calculateAvgCourseCalification(String nameCourse) throws Exception {
//		double result = 0;
//		double sumatoryCalification = 0;
//		int quantityCalification = 0;
//		Course course = getCourse(nameCourse);
//		if (course.getNameActivity().equalsIgnoreCase(nameCourse)) {
//			Iterator<Homework> itHomework = course.getHomeworkList().inOrder();
//			while (itHomework.hasNext()) {
//				quantityCalification++;
//				sumatoryCalification += itHomework.next().getCalification();
//			}
//			result = sumatoryCalification / quantityCalification;
//			return result;
//		}
//		return result;
//	}
//
//	public double calculateTotalAvgCalification() throws Exception {
//		double result = 0;
//		double sumatoryCalification = 0;
//		int quantityCourses = 0;
//		Iterator<Course> itCourse = getCourseList().inOrder();
//		while (itCourse.hasNext()) {
//			quantityCourses++;
//			sumatoryCalification += calculateAvgCourseCalification(itCourse.next().getNameActivity());
//		}
//		result = sumatoryCalification / quantityCourses;
//		return result;
//	}

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
