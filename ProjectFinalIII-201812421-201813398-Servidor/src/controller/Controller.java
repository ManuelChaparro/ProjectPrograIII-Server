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
			Logger.getGlobal().log(Level.INFO, ConstantsCnt.SERVER_STAR_RUN);
			while(true) {
				Socket socket = server.accept();
				new Client(socket).start();
				Logger.getGlobal().log(Level.INFO, ConstantsCnt.MESSAGE_ACCEPT_NEW_CONNECTION);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logger.getGlobal().log(Level.WARNING, ConstantsCnt.ERROR_MESSAGE_SERVER_ALREADY_RUN);
		}
	}
}
