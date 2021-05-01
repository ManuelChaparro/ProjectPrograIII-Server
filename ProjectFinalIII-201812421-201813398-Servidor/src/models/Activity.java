package models;

public class Activity {
	
	private String nameActivity;
	private String descriptionActivity;
	private String scheduleActivity;
	
	public Activity(String nameActivity, String descriptionActivity, String scheduleActivity) {
		this.nameActivity = nameActivity;
		this.descriptionActivity = descriptionActivity;
		this.scheduleActivity = scheduleActivity;
	}
	
	public Activity(String nameActivity) {
		this.nameActivity = nameActivity;
	}

	public String getNameActivity() {
		return nameActivity;
	}

	public String getDescriptionActivity() {
		return descriptionActivity;
	}

	public void setDescriptionActivity(String descriptionActivity) {
		this.descriptionActivity = descriptionActivity;
	}

	public String getScheduleActivity() {
		return scheduleActivity;
	}

	public void setScheduleActivity(String scheduleActivity) {
		this.scheduleActivity = scheduleActivity;
	}
}
