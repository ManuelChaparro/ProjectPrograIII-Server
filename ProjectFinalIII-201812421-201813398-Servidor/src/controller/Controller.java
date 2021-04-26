package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    
	private ServerSocket server;
	private static final int PORT =24211;
	
	public Controller() {
		try {
			server = new ServerSocket(PORT);
			Logger.getGlobal().log(Level.INFO, "Arranco el servidor.");
			while(true) {
				Socket socket = server.accept();
				new Client(socket).start();
				Logger.getGlobal().log(Level.INFO, "Nueva conexion aceptada");
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logger.getGlobal().log(Level.WARNING, "Servidor ya se encuentra corriendo.");
		}
	}
}
