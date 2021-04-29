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
			Logger.getGlobal().log(Level.INFO, "Una conexion finalizada.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initApp() throws Exception {
		if (conection.receiveBoolean()) {
			Gson gson = new Gson();
			String option = conection.receiveUTF();
			Student user = gson.fromJson(option.toString(), Student.class);
			if (modelsManager.isExistStudent(user.getCodeUser(), user.getPassword())) {
				conection.sendBoolean(true);
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
	}

	private void save() throws Exception {
		GSONFileManager.writeFile(new ArchiveClass(modelsManager.getStudentTree(), modelsManager.getTeacherTree(),
				modelsManager.getAvailableCourse(), modelsManager.getCourseGeneralList()));
	}

	private void options() throws Exception {
		String option = conection.receiveUTF();
		switch (option) {
		case "SHOW_SCHEDULE":
			conection.sendUTF("Mostrando horario...");
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
			String[] xd = conection.receiveUTF().split(";;;");
			try {
				modelsManager.assignStudentCourse(xd[0], xd[1], xd[2]);
				conection.sendBoolean(true);
				save();
			} catch (Exception e) {
				e.printStackTrace();
				conection.sendBoolean(false);
			}
			break;
		case "MODIFY_COURSE_ST":
			conection.sendUTF(modelsManager.getStudentCourses(conection.receiveUTF()));
			break;
		case "FIND_HOMEWORK":
			String code = conection.receiveUTF();
			String course = conection.receiveUTF();
			conection.sendUTF(modelsManager.getStudentHomeworks(code, course));
			break;
		case "FIND_INFO_HOMEWORK":
			if (!conection.receiveBoolean()) {
				String[] dataFindHomework = conection.receiveUTF().split(";;;");
				conection.sendUTF(modelsManager.getSpecificStudentHomework(dataFindHomework[0], dataFindHomework[1],
						dataFindHomework[2]));
				;
			}
			break;
		case "ADD_OR_MODIFY_HOMEWORK":
			if (conection.receiveBoolean()) {
				System.out.println(1);
				String[] newHomework = conection.receiveUTF().split(";;;");
				for (String string : newHomework) {
					System.out.println(string);
				}
				modelsManager.addStudentHomework(newHomework[0], newHomework[1], newHomework[2], newHomework[3],
						Double.parseDouble(newHomework[4]));
			} else {
				System.out.println(2);
				String[] newHomework = conection.receiveUTF().split(";;;");
				for (String string : newHomework) {
					System.out.println(string);
				}
				modelsManager.modifySpecificHomework(newHomework[0], newHomework[1], newHomework[2], newHomework[3],
						Double.parseDouble(newHomework[4]));
			}
			save();
			break;
		default:
			break;
		}
		options();
	}
}