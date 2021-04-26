package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.ModelsManager;
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
		String option = conection.receiveUTF();
		System.out.println(option);
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
