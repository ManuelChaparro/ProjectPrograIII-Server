package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import models.Course;
import models.Student;
import models.Teacher;
import structures.AVLtree;

public class ArchiveClass {

	private ArrayList<Course> courseGeneralList;
	private ArrayList<Student> students;
	private ArrayList<Teacher> teachers;
	private ArrayList<String> availableCourses;

	public ArchiveClass(AVLtree<Student> students, AVLtree<Teacher> teachers, AVLtree<String> courses,
			ArrayList<Course> courseGeneralList) {
		this.students = new ArrayList<Student>();
		this.teachers = new ArrayList<Teacher>();
		this.availableCourses = new ArrayList<String>();
		this.courseGeneralList = new ArrayList<Course>();
		initSave(students, teachers, courses, courseGeneralList);
	}

	private void initSave(AVLtree<Student> students, AVLtree<Teacher> teachers, AVLtree<String> courses,
			ArrayList<Course> courseGeneralList) {
		saveStudents(students);
		saveTeachers(teachers);
		saveCourses(courses);
		this.courseGeneralList = courseGeneralList;
	}

	private void saveStudents(AVLtree<Student> auxStudents) {
		Iterator<Student> it = auxStudents.inOrder();
		while (it.hasNext()) {
			students.add((Student) it.next());
		}
	}

	private void saveTeachers(AVLtree<Teacher> auxTeachers) {
		Iterator<Teacher> it = auxTeachers.inOrder();
		while (it.hasNext()) {
			teachers.add((Teacher) it.next());
		}
	}

	private void saveCourses(AVLtree<String> courses) {
		Iterator<String> it = courses.inOrder();
		while (it.hasNext()) {
			availableCourses.add((String) it.next());
		}
	}

	public ArrayList<Teacher> getTeachers() {
		return teachers;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public ArrayList<Course> getCourseGeneralList() {
		return courseGeneralList;
	}

	public ArrayList<String> getAvailableCourses() {
		return availableCourses;
	}
}