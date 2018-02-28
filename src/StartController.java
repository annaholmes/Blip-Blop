import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StartController {

    @FXML
    private Button start;

    @FXML
    private Button join;

    @FXML
    private TextField ipField;

    @FXML
    private TextArea yourMessage;

    @FXML
    private TextArea peerMessage;

    @FXML
    private Button send;

    private Peer2 peer;

    public void initialize() {
        try {
            this.peer = new Peer2(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startDrawing();
            }
        });
        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                joinDrawing(ipField.getText());
            }
        });
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                sendText(send.getText());
            }
        });
    }


    public void startDrawing() {
        peer.launchServer();
    }


    public void joinDrawing(String ip) {
        peer.connectToServer(ip);
    }

    public void sendText(String message){
        peer.sendToServer(message);

    }
}
