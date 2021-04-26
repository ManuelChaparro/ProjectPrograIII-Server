package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import models.Course;
import models.ModelsManager;
import models.User;
import net.Conection;

public class Client extends Thread{
	
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
		}
	}
	
	private void initApp() throws IOException {
		if (conection.receiveBoolean()) {
			Gson gson = new Gson();
			String option = conection.receiveUTF();
			User user = gson.fromJson(option.toString(), User.class);
			System.out.println(user.getCodeUser());
		}else {
			System.out.println("Crear usuario");
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
	
	private void getAvgCalification(){
		
	}
	
	private void manageCourseAdmin() {
		
	}
}
