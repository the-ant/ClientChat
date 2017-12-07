package application;

import java.io.IOException;
import java.net.Socket;

public class Client {

	// private static final int CONNECT_TIME_OUT = 30000;
	private static final String IP_ADDRESS = "127.0.0.1";
	private static final int PORT = 5151;

	private ClientConnection clientConnection;
	private Socket socket;
	private boolean connected = false;

	public Client(ClientController controller) {
		initClient(controller);
	}

	private void initClient(ClientController controller) {
		try {
			socket = new Socket(IP_ADDRESS, PORT);
			if (socket != null && socket.isConnected()) {
				clientConnection = new ClientConnection(socket, controller);
				connected = true;
				controller.Screen.appendText("Đã nối đến Server!\n");
			}
		} catch (IOException e) {
			controller.reConnectBtn.setDisable(false);
			controller.Screen.appendText("Không thể kết nối đến Server!\n");
		}
	}

	public void startClient() {
		if (socket != null && socket.isConnected()) {
			clientConnection.start();
		}
	}

	public void closeClient() {
		if (socket != null && socket.isConnected()) {
			send("#quit");
			clientConnection.close();
			clientConnection.interrupt();
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String msg) {
		if (socket != null && socket.isConnected()) {
			clientConnection.sendMessage(msg);
		}
	}

	public boolean isConnected() {
		return connected;
	}
}
