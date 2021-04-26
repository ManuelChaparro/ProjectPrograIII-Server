package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Conection {
	
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	public Conection(Socket socket) throws IOException {
		this.socket = socket;
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}

	public String receiveUTF() throws IOException {
		return input.readUTF();
	}
	
	public void sendUTF(String info) {
		
	}
	
	public boolean receiveBoolean() {
		return true;
	}
	
	public void sendBoolean(boolean info) throws IOException {
		output.writeBoolean(info);
	}
	
	public void closeConection() throws IOException {
		socket.close();
	}
}
