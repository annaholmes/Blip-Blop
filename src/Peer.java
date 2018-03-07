import com.sun.javafx.tk.Toolkit;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Queue;

public class Peer  {

    private PeerServer server;
    private PeerClient client;
    private int port = 3000;
    private PeerData data;


    Stage stage;

    @FXML
    TextField host;

    @FXML
    TextArea outgoing;

    @FXML
    TextArea incoming;



    public Peer() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        data = new PeerData(ip);
        server = new PeerServer(port, data);

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

        /*Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("handling text");
                Platform.runLater(() -> {
                    synchronized (data) {
                        System.out.println(data.getMessage());
                        if (data.isNewMessage()) {
                            System.out.println("hey");
                            int message = data.getMessage();
                            System.out.println("message " + message);
                            data.setNewMessage(false);
                            incoming.setText(incoming.getText() + String.valueOf(data.getMessage()));
                        }
                    }
                });
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();*/
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (true)
                {
                    //synchronized (data) {
                        if (data.isNewMessage()) {
                            Platform.runLater(() -> incoming.setText(incoming.getText() + data.getMessage()));
                            data.setNewMessage(false);
                        }
                    //}
                    Thread.sleep (5000);
                    System.out.println("update");
                }
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    @FXML
    void startDrawing() {
        try {
            server.listen();
            // for switching to new scene
            /*try {
                Main.getPrimaryStage().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Blip-BlopGUI.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Main.getPrimaryStage().setScene(new Scene(root1));
                Main.getPrimaryStage().show();
            } catch(Exception e) {
                e.printStackTrace();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("started listening...");

    }

    @FXML
    void updateDrawing() {
        incoming.setText(String.valueOf(data.getMessage()));
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
        int message = Integer.valueOf(outgoing.getText());
        ArrayList<String> queue;
        String peer;
        //synchronized (data) {
            peer = data.cyclePeers();
            queue = data.getPeers();
        //}
        client = new PeerClient(peer, port);
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connection opened");

        try {
            client.sendToServer(queue);
            client.sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("text sent");

        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connection closed");
        // take string from text field
        // grab peer queue from server (getQueue)
        // dequeue peer from queue & enqueue
        // client send message to peer
    }

}
