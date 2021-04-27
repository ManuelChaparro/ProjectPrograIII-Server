package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import models.Course;
import models.Homework;
import models.InterfaceSerializer;
import models.ModelsManager;
import models.Student;
import models.User;
import net.Conection;
import persistence.GSONFileManager;

public class Client extends Thread {

	private Conection conection;
	private ModelsManager modelsManager;
	private Socket socketClient;
	private JsonParser json;
	private Gson gson;

	public Client(Socket socket) throws IOException {
		this.socketClient = socket;
		conection = new Conection(socketClient);
		json = new JsonParser();
		gson = new Gson();
//		loadModelsManager();
		saveModelsManager();
	}

	private void saveModelsManager() {
		for
		
	}

	private void loadModelsManager() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		modelsManager = new ModelsManager();
		modelsManager = GSONFileManager.readFile();
		try {
			System.out.println(modelsManager.getGeneralCourse("PROGRAMACION III").getNameActivity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			initApp();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getGlobal().log(Level.INFO, "Una conexion finalizada.");
		}
	}

	private void initApp() throws Exception {
		if (conection.receiveBoolean()) {
			Gson gson = new Gson();
			String option = conection.receiveUTF();
			User user = gson.fromJson(option.toString(), User.class);
		} else {
			String user = conection.receiveUTF();
			Student student = gson.fromJson(user.toString(), Student.class);
			if (modelsManager.isExistStudent(student.getCodeUser())) {
				conection.sendBoolean(true);
			} else {
				conection.sendBoolean(false);
				modelsManager.createStudent(student);
				System.out.println(modelsManager.isExistStudent(student.getCodeUser()));
				GSONFileManager.writeFile(modelsManager);
			}
		}
	}

	private void createUser() {

	}

	private void manageCourseUser() {

	}

	private void manageActivities() {

	}

	private void getClassSchedule() {

	}

	private void getAvgCalification() {

	}

	private void manageCourseAdmin() {

	}
}
