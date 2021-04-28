package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import models.ModelsManager;
import models.Student;
import net.Conection;
import persistence.GSONFileManager;
import persistence.ArchiveClass;

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
				GSONFileManager.writeFile(new ArchiveClass(modelsManager.getStudentTree(), modelsManager.getTeacherTree(),
						modelsManager.getAvailableCourse(), modelsManager.getCourseGeneralList()));
				conection.sendBoolean(true);
				initApp();
			}
		}
	}
	
	private void options() throws Exception {
		int option = conection.receiveInt();
		switch (option) {
		case 1:
//			addContact();
			break;
		case 2:
			conection.sendUTF(modelsManager.getStringAvailableCourses());
			break;
		case 3:
			String string = conection.receiveUTF();
			conection.sendUTF(modelsManager.getAvailableTeachers(string));
			break;
		case 4:
//			conection.sendUTF(contactRecord.getContactListString());
			break;
		case 5:
//			showContactsStatistic();
			break;
		default:
			break;
		}
		options();
		conection.closeConection();
	}
}