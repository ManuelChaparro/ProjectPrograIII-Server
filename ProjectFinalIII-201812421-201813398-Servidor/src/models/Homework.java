package models;

public class Homework {

	private String nameHomework;
	private String annotation;
	private double calification;

	public Homework(String nameHomework, String annotation, double calification) {
		this.nameHomework = nameHomework;
		this.annotation = annotation;
		this.calification = calification;
	}
	
	public Homework(String nameHomework) {
		this.nameHomework = nameHomework;
	}

	public String getNameHomework() {
		return nameHomework;
	}

	public void setNameHomework(String nameHomework) {
		this.nameHomework = nameHomework;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public double getCalification() {
		return calification;
	}

	public void setCalification(double calification) {
		this.calification = calification;
	}

	@Override
	public String toString() {
		return "Homework [nameHomework=" + nameHomework + ", annotation=" + annotation + ", calification="
				+ calification + "]";
	}	
	
	
}
