import com.sun.javafx.tk.Toolkit;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.embed.swing.SwingFXUtils;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ResourceBundle;

public class Peer implements Initializable {

    private PeerServer server;
    private PeerClient client;
    private int port = 3000;
    private PeerData data;



    GraphicsContext context;


    Stage stage;

    //@FXML
    //Canvas canvas;

    @FXML
    Button updateDrawing;

    @FXML
    Pane imgPane;

    @FXML
    VBox startSide;

   @FXML
   HBox drawingSide;

    @FXML
    Button save;

    @FXML
    Button send;

    @FXML
    ColorPicker colorPicker;

    @FXML
    AnchorPane toAttach;

    @FXML
    TextField host;

    @FXML
    TextArea outgoing;

    @FXML
    Canvas pCanvas;


    @FXML
    Canvas startCanvas;

    @FXML
    TextArea incoming;

    Image toWrite;


    @FXML
    TextField yourIP;

    @FXML
    ArrayList<byte[]> imgArray;


    // big credit for canvas drawing!
    // https://stackoverflow.com/questions/43429251/how-to-draw-a-continuous-line-with-mouse-on-javafx-canvas


    public Peer() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        data = new PeerData(ip, this);
        server = new PeerServer(port, data);
        //pCanvas = new Canvas();



    }



    public void updateDrawing(byte[] b) {
        Platform.runLater(() -> pCanvas.getGraphicsContext2D().drawImage(new Image(new ByteArrayInputStream(b)), 0, 0, pCanvas.getWidth(), pCanvas.getHeight()));
    }

    @FXML
    void startDrawing() {
        try {
            server.listen();
            startSide.setDisable(true);
            drawingSide.setDisable(false);
            // for switching to new scene
            /*try {
                Platform.runLater(() -> {
                    //Main.getPrimaryStage().hide();
                    Parent root1 = null;
                    try {
                        root1 = FXMLLoader.load(getClass().getResource("Blip-BlopGUI.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Main.getPrimaryStage().setScene(new Scene(root1));
                    Main.getPrimaryStage().show();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("started listening...");

    }

    @FXML
    void saveDrawing() {
        WritableImage wim = new WritableImage(((Double) pCanvas.getWidth()).intValue(), ((Double) pCanvas.getHeight()).intValue());
        Image snapshot = pCanvas.snapshot(null, wim);
        BufferedImage bI = SwingFXUtils.fromFXImage(snapshot, null);
        File f = new File("pix/" + String.valueOf(System.currentTimeMillis()) + ".png");
        try {
            ImageIO.write(bI, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void joinDrawing() {
        String ip = "";
        try {
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startSide.setDisable(true);
        drawingSide.setDisable(false);
        System.out.println("started listening...");
        /*try {
            Main.getPrimaryStage().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Blip-BlopGUI.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Main.getPrimaryStage().setScene(new Scene(root1));
            Main.getPrimaryStage().show();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
        System.out.println(pCanvas.getWidth() + " " + pCanvas.getHeight());
        WritableImage wim = new WritableImage(((Double) pCanvas.getWidth()).intValue(), ((Double) pCanvas.getHeight()).intValue());
        //int message = Integer.valueOf(outgoing.getText());
        Image snapshot = pCanvas.snapshot(null, wim);
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
            client.sendToServer(byteArray);
            //client.sendToServer(byteArray);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            drawingSide.setDisable(true);
            String ip = "";
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            yourIP.setText("Your IP is " + ip);

            yourIP.setFocusTraversable(false);
            yourIP.setEditable(false);

            GraphicsContext graphicsContext = pCanvas.getGraphicsContext2D();
            System.out.println("doin this");
            System.out.println(imgPane == null);



            pCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                            graphicsContext.stroke();

                        }
                    });

            pCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                            graphicsContext.closePath();
                            graphicsContext.beginPath();
                            graphicsContext.moveTo(event.getX(), event.getY());
                        }
                    });

            pCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            graphicsContext.lineTo(event.getX(), event.getY());
                            graphicsContext.stroke();
                            graphicsContext.closePath();
                        }
                    });

        colorPicker.setOnAction(new EventHandler() {
            @Override
            public void handle(javafx.event.Event event) {
                graphicsContext.setStroke(colorPicker.getValue());
            }
        });

    }
}



