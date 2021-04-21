package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Conection {
	
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	public Conection(Socket socket) {
		
	}

	public String receiveUTF() {
		return "";
	}
}
