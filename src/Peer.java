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
import javafx.embed.swing.SwingFXUtils;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
    Canvas canvas;

    @FXML
    Button save;

    @FXML
    Button send;

    @FXML
    TextField host;

    @FXML
    TextArea outgoing;

    @FXML
    TextArea incoming;



    @FXML
    TextField yourIP;



    // big credit for canvas drawing!
    // https://stackoverflow.com/questions/43429251/how-to-draw-a-continuous-line-with-mouse-on-javafx-canvas
    public void initialize() {
        if (yourIP != null) {
            String ip = "";
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            yourIP.setText("Your IP is " + ip);

            yourIP.setFocusTraversable(false);
            yourIP.setEditable(false);
        } else if (canvas != null) {
            final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event) {
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                            graphicsContext.stroke();

                        }
                    });

            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                    new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event) {
                            graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                            graphicsContext.closePath();
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                        }
                    });

            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                    new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event) {
                            graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                            graphicsContext.closePath();
                        }
                    });
            Task task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    while (true)
                    {
                        //synchronized (data) {
                        if (data.isNewMessage()) {
                            //Platform.runLater(() -> incoming.setText(incoming.getText() + data.getMessage()));
                            graphicsContext.drawImage(data.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
                            data.setNewMessage(false);
                        }
                        //}
                        Thread.sleep (3000);
                        System.out.println("update");
                    }
                }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }


    }


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

    }

    @FXML
    void startDrawing() {
        try {
            server.listen();
            // for switching to new scene
            try {
                Main.getPrimaryStage().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Blip-BlopGUI.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Main.getPrimaryStage().setScene(new Scene(root1));
                Main.getPrimaryStage().show();

            } catch(Exception e) {
                e.printStackTrace();
            }
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
        WritableImage wim = new WritableImage(((Double) canvas.getWidth()).intValue(), ((Double) canvas.getHeight()).intValue());
        //int message = Integer.valueOf(outgoing.getText());
        Image snapshot = canvas.snapshot(null, wim);
        BufferedImage bI = SwingFXUtils.fromFXImage(snapshot, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bI, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] byteArray = outputStream.toByteArray();


        ArrayList<String> queue;
        String peer;
        //synchronized (data) {
        peer = data.cyclePeers();
        queue = data.getPeers();

        System.out.println(queue);
        //}
        client = new PeerClient(peer, port);
        System.out.println(peer);
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("connection opened");
        try {
            client.sendToServer(queue);
            //client.sendToServer(message);
            client.sendToServer(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("item sent");

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
