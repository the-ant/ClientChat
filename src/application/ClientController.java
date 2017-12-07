package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientController implements Initializable {
	
	@FXML
	public TextArea Screen;
	@FXML
	private TextField tfMessage, tfUserName;
	@FXML
	private Button sendBtn;
	@FXML
	public Button reConnectBtn;
	
	private Stage primaryStage = Main.getPrimaryStage();
	private Client client;
	private String userName = "";
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		client = new Client(this);
		client.startClient();
		primaryStage.setOnCloseRequest(e -> client.closeClient());
	}
	
	public void handleSendMessage(MouseEvent e) {
		String msg = tfMessage.getText().toString();
		if (client!= null && !msg.equals("")) {
			client.send(userName + ": " + msg);
			Screen.appendText("Me: " + msg + "\n");
			tfMessage.clear();
		}
	}
	
	public void handleConfirmUserName(MouseEvent e) {
		if (!tfUserName.getText().equals("")) {
			userName = tfUserName.getText().toString();
		}
	}
	
	public void handleReConnect(MouseEvent e) {
		if (!client.isConnected()) {
			client = new Client(this);
			client.startClient();
		}
	}
}
