package persistence;

import java.util.ArrayList;
import java.util.Iterator;

import models.Course;
import models.Student;
import models.Teacher;
import structures.AVLtree;

public class Save {
	
	ArrayList<Student> students;
	ArrayList<Teacher> teachers;
	ArrayList<Course> availableCourses;
	
	public Save(AVLtree<Student> students, AVLtree<Teacher> teachers, AVLtree<Course> courses) {
		saveStudents(students);
		saveTeachers(teachers);
		saveCourses(courses);
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

	private void saveCourses(AVLtree<Course> courses) {
		Iterator<Course> it = courses.inOrder();
		while (it.hasNext()) {
			availableCourses.add((Course) it.next());
		}
	}
}
