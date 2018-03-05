import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Queue;

public class Peer  {

    private PeerServer server;
    private PeerClient client;
    private int port = 3000;

    @FXML
    TextField host;

    @FXML
    TextArea outgoing;

    @FXML
    TextArea incoming;


    public Peer() {
        server = new PeerServer(port);

        /*try {
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.sendToServer("fat pikachu");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (server.isNewMessage()) {
                    int message = server.getMessage();
                    server.setNewMessage(false);
                    incoming.setText(incoming.getText() + " " + String.valueOf(message));
                }
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    @FXML
    void startDrawing() {
        try {
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("started listening...");

    }

    @FXML
    void joinDrawing() {
        String ip = "";
        try {
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("started listening...");
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String peer = host.getText();
        client = new PeerClient(peer, port);
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connection opened");

        try {
            client.sendToServer(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ip sent");

        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connection closed");
    }

    @FXML
    void sendDrawing() {
        // take string from text field
        // grab peer queue from server (getQueue)
        // dequeue peer from queue & enqueue
        // client send message to peer


    }



    public static void main(String[] args) {
        new Peer();
    }






}
