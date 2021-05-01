package models;

public class ExternalActivity extends Activity {

	public ExternalActivity(String nameExActivity, String descriptionExActivity, String scheduleExActivity) {
		super(nameExActivity, descriptionExActivity, scheduleExActivity);
	}

	@Override
	public String toString() {
		return getNameActivity() + "&" + getDescriptionActivity() + "&" + getScheduleActivity();
	}
}
