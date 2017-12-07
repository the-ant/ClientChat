package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread {

	private ClientController controller;
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private boolean running = true;

	public ClientConnection(Socket socket, ClientController controller) {
		this.controller = controller;
		this.socket = socket;
		try {
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void sendMessage(String msg) {
		try {
			dataOut.writeUTF(msg);
			dataOut.flush();
		} catch (IOException e) {
			try {
				dataIn.close();
				dataOut.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		try {
			while (running) {
				while (dataIn.available() == 0) {
				}

				String msg = dataIn.readUTF();
				controller.Screen.appendText(msg + "\n");
			}

			dataIn.close();
			dataOut.close();
			socket.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	
	public void close() {
		sendMessage("#quit");
		this.running = false;
	}
}
