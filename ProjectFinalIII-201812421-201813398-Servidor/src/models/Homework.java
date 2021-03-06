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

	public String getNameHomework() {
		return nameHomework;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setCalification(double calification) {
		this.calification = calification;
	}

	public double getCalification() {
		return calification;
	}

	@Override
	public String toString() {
		return nameHomework + ConstantsModels.SEPARATOR_Y_SPECIAL + annotation + ConstantsModels.SEPARATOR_Y_SPECIAL
				+ calification;
	}
}
