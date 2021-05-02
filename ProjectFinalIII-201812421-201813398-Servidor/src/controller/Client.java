package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import models.ModelsManager;
import models.Student;
import net.Conection;
import persistence.ArchiveClass;
import persistence.GSONFileManager;

public class Client extends Thread {

	private Conection conection;
	private ModelsManager modelsManager;
	private Socket socketClient;

	public Client(Socket socket) throws IOException {
		this.socketClient = socket;
		conection = new Conection(socketClient);
		modelsManager = new ModelsManager();
	}

	public void run() {
		try {
			initApp();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.INFO, ConstantsCnt.ERROR_MESSAGE_FAILED_CONNECTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initApp() throws Exception {
		if (conection.receiveBoolean()) {
			Gson gson = new Gson();
			String option = conection.receiveUTF();
			Student user = gson.fromJson(option.toString(), Student.class);
			if (modelsManager.validateStudentLogin(user.getCodeUser(), user.getPassword())) {
				conection.sendBoolean(true);
				conection.sendUTF(modelsManager.getStudentName(conection.receiveUTF()));
				options();
			} else {
				conection.sendBoolean(false);
				initApp();
			}
		} else {
			Gson gson = new Gson();
			String option = conection.receiveUTF();
			Student user = gson.fromJson(option.toString(), Student.class);
			if (modelsManager.isExistStudent(user.getCodeUser())) {
				conection.sendBoolean(false);
				initApp();
			} else {
				modelsManager.createStudent(user);

				save();
				conection.sendBoolean(true);
				initApp();
			}
		}
		conection.closeConection();
	}

	private void save() throws Exception {
		GSONFileManager.writeFile(new ArchiveClass(modelsManager.getStudentTree(), modelsManager.getTeacherTree(),
				modelsManager.getAvailableCourse(), modelsManager.getCourseGeneralList()));
	}

	private void options() throws Exception {
		String option = conection.receiveUTF();
		String code = ConstantsCnt.EMPTY_STRING;
		switch (option) {
		case "SHOW_SCHEDULE":
			code = conection.receiveUTF();
			conection.sendUTF(modelsManager.getStudentCompleteCourses(code) + ConstantsCnt.SEPARATOR_FIVE_SLEEP_LINES
					+ modelsManager.getStudentTotalExternalActivities(code));
			break;
		case "ACTION_SCHEDULER_BTN":
			code = conection.receiveUTF();
			String info = conection.receiveUTF();
			String course = modelsManager.getStudentSpecifiCourse(code, info);
			if (course.equalsIgnoreCase(ConstantsCnt.EMPTY_STRING)) {
				String activity = modelsManager.getStudentSpecificExternalActivity(code, info);
				conection.sendBoolean(true);
				conection.sendUTF(activity);
			} else {
				conection.sendBoolean(false);
				conection.sendUTF(course);
			}
			break;
		case "ADD_COURSE_ST":
			conection.sendUTF(modelsManager.getAvailableCourses());
			break;
		case "FIND_TEACHERS":
			conection.sendUTF(modelsManager.getAvailableTeachers(conection.receiveUTF()));
			break;
		case "FIND_INFO_ADD_COURSE":
			conection.sendUTF(modelsManager.getInfoSchedule(conection.receiveUTF(), conection.receiveUTF()));
			break;
		case "INSERT_COURSE":
			String[] data = conection.receiveUTF().split(ConstantsCnt.SEPARATOR_THREE_DOT_AND_COMA);
			try {
				modelsManager.assignStudentCourse(data[0], data[1], data[2]);
				conection.sendBoolean(true);
				save();
			} catch (Exception e) {
				conection.sendBoolean(false);
			}
			break;
		case "MODIFY_COURSE_ST":
			conection.sendUTF(modelsManager.getStudentCourses(conection.receiveUTF()));
			break;
		case "FIND_HOMEWORK":
			code = conection.receiveUTF();
			course = conection.receiveUTF();
			conection.sendUTF(modelsManager.getStudentHomeworks(code, course));
			break;
		case "FIND_INFO_HOMEWORK":
			if (!conection.receiveBoolean()) {
				String[] dataFindHomework = conection.receiveUTF().split(ConstantsCnt.SEPARATOR_THREE_DOT_AND_COMA);
				conection.sendUTF(modelsManager.getSpecificStudentHomework(dataFindHomework[0], dataFindHomework[1],
						dataFindHomework[2]));
			}
			break;
		case "ADD_OR_MODIFY_HOMEWORK":
			if (conection.receiveBoolean()) {
				try {
					String[] newHomework = conection.receiveUTF().split(ConstantsCnt.SEPARATOR_THREE_DOT_AND_COMA);
					modelsManager.addStudentHomework(newHomework[0], newHomework[1], newHomework[2], newHomework[3],
							Double.parseDouble(newHomework[4]));
					conection.sendBoolean(true);
				} catch (Exception e) {
					conection.sendBoolean(false);
				}
			} else {
				try {
					String[] newHomework = conection.receiveUTF().split(ConstantsCnt.SEPARATOR_THREE_DOT_AND_COMA);
					modelsManager.modifySpecificHomework(newHomework[0], newHomework[1], newHomework[2], newHomework[3],
							Double.parseDouble(newHomework[4]));
					conection.sendBoolean(true);
				} catch (Exception e) {
					conection.sendBoolean(false);
				}
			}
			save();
			break;
		case "DELETE_COURSE_OR_HOMEWORK":
			conection.sendUTF(modelsManager.getStudentCourses(conection.receiveUTF()));
			break;
		case "FIND_HOMEWORK_DELETE":
			conection.sendUTF(modelsManager.getStudentHomeworks(conection.receiveUTF(), conection.receiveUTF()));
			break;
		case "CONFIRM_DELETE_COURSE":
			modelsManager.cancelStudentCourse(conection.receiveUTF(), conection.receiveUTF());
			save();
			break;
		case "CONFIRM_DELETE_HOMEWORK":
			modelsManager.cancelStudentHomework(conection.receiveUTF(), conection.receiveUTF(), conection.receiveUTF());
			save();
			break;
		case "ADD_OR_MOD_ACTIVITY_ST":
			conection.sendUTF(modelsManager.getStudentExternalActivities(conection.receiveUTF()));
			break;
		case "FIND_MODIFY_HOMEWORK":
			conection.sendUTF(
					modelsManager.getStudentSpecificExternalActivity(conection.receiveUTF(), conection.receiveUTF()));
			break;
		case "SEND_ACTIVITY":
			try {
				if (conection.receiveBoolean()) {
					code = conection.receiveUTF();
					data = conection.receiveUTF().split(ConstantsCnt.SEPARATOR_THREE_DOT_AND_COMA);
					modelsManager.addStudentExternalActivity(code, data[0], data[1], data[2]);
				} else {
					code = conection.receiveUTF();
					data = conection.receiveUTF().split(ConstantsCnt.SEPARATOR_THREE_DOT_AND_COMA);
					modelsManager.modifyExternalActivity(code, data[0], data[1], data[2]);
				}
				conection.sendBoolean(true);
			} catch (Exception e) {
				conection.sendBoolean(false);
			}
			conection.sendUTF(modelsManager.getStudentExternalActivities(code));
			save();
			break;
		case "DELETE_ACTIVITY_ST":
			conection.sendUTF(modelsManager.getStudentExternalActivities(conection.receiveUTF()));
			break;
		case "CONFIRM_DELETE_ACTIVITY":
			code = conection.receiveUTF();
			String deleteActivity = conection.receiveUTF();
			modelsManager.cancelExternalActivity(code, deleteActivity);
			conection.sendUTF(modelsManager.getStudentExternalActivities(code));
			save();
			break;
		case "AVG_ST":
			conection.sendUTF(modelsManager.getStudentCourses(conection.receiveUTF()));
			break;
		case "CALCULATE_AVG":
			code = conection.receiveUTF();
			conection.sendUTF(
					String.valueOf(modelsManager.calculateAvgCourseCalification(code, conection.receiveUTF())));
			conection.sendUTF(String.valueOf(modelsManager.calculateTotalAvgCalification(code)));
			break;
		default:
			break;
		}
		options();
	}
}