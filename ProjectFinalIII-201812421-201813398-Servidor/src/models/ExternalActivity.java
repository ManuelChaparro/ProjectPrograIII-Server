package models;

public class ExternalActivity extends Activity {

	public ExternalActivity(String nameExActivity, String descriptionExActivity, String scheduleExActivity) {
		super(nameExActivity, descriptionExActivity, scheduleExActivity);
	}

	@Override
	public String toString() {
		return getNameActivity() + ConstantsModels.SEPARATOR_Y_SPECIAL + getDescriptionActivity()
				+ ConstantsModels.SEPARATOR_Y_SPECIAL + getScheduleActivity();
	}
}
