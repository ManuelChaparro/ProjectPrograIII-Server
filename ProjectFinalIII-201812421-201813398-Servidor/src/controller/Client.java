package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import models.ModelsManager;
import models.Student;
import net.Conection;

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
			conection.sendUTF(modelsManager.getAvailableCourses());
			break;
		case 3:
//			deleteContact();
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