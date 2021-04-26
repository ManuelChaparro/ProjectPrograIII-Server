package models;

import java.util.Comparator;
import java.util.Iterator;

import structures.DoubleList;

public class Course extends Activity {

	private String nameCourseTeacher;
	private DoubleList<Homework> homeworkList;

	public Course(String nameCourse, String descriptionCourse, String scheduleCourse) {
		super(nameCourse, descriptionCourse, scheduleCourse);
		homeworkList = new DoubleList<Homework>(homeworkComparator());
		this.nameCourseTeacher = "";
	}

	public Course(String nameCourse) {
		super(nameCourse);
	}

	public void setNameCourseTeacher(String nameCourseTeacher) {
		this.nameCourseTeacher = nameCourseTeacher;
	}
	
	public String getNameCourseTeacher() {
		return nameCourseTeacher;
	}

	public void addHomework(Homework homework) {
		homeworkList.insert(homework);
	}

	public void deleteHomework(String nameHomework) {
		homeworkList.remove(new Homework(nameHomework));
	}

	public void modifyHomeworkAnnotation(String nameHomework, String annotation) throws Exception {
		getHomework(nameHomework).setAnnotation(annotation);
	}
	
	public void modifyHomeworkCalification(String nameHomework, double calification) throws Exception {
		getHomework(nameHomework).setCalification(calification);
	}

	public DoubleList<Homework> getHomeworkList() {
		return homeworkList;
	}

	public Homework getHomework(String nameHomework) throws Exception {
		Iterator<Homework> it = homeworkList.iterator();
		while (it.hasNext()) {
			if (it.next().getNameHomework().equalsIgnoreCase(nameHomework)) {
				return it.next();
			}
		}
		throw new Exception("La tarea que busca no existe.");
	}

	private Comparator<Homework> homeworkComparator() {
		return new Comparator<Homework>() {
			public int compare(Homework homeworkOne, Homework homeworkTwo) {
				int compare = homeworkOne.getNameHomework().compareToIgnoreCase(homeworkTwo.getNameHomework());
				if (compare == 0) {
					return 0;
				} else {
					return 1;
				}
			}
		};
	}
}