import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TalkClientController {
	@FXML
	TextArea outgoing;
	
	@FXML
	TextArea incoming;
	
	@FXML
	Button send;
	
	@FXML
	TextField host;
	
	@FXML
	TextField port2;

	int port;
	
	@FXML
	void initialize() {
		incoming.setEditable(false);
		send.setOnAction(event -> send());
	}
	
	void badNews(String what) {
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(what);
		badNum.show();
	}
	
	void send() {
		sendTo(host.getText(), this.port, outgoing.getText());

	}
	
	void sendTo(String host, int port, String message) {
		new Thread(() -> {
			try {
				Socket target = new Socket(host, port);
				send(target, message);
				receive(target);
				target.close();
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}
	
	void send(Socket target, String message) throws IOException {
		PrintWriter sockout = new PrintWriter(target.getOutputStream());
		sockout.println(message);
		sockout.flush();		
	}
	
	void receive(Socket target) throws IOException {
		BufferedReader sockin = new BufferedReader(new InputStreamReader(target.getInputStream()));
		while (!sockin.ready()) {}
		while (sockin.ready()) {
			try {
				String msg = sockin.readLine();
				Platform.runLater(() -> {incoming.setText(incoming.getText() + '\n' + msg);});
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}		
	}
}
