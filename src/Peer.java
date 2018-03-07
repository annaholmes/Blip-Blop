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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.embed.swing.SwingFXUtils;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    /*public void initialize() {
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
            context = canvas.getGraphicsContext2D();
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

            //canvas.widthProperty().addListener(event -> addImageToCanvas(imageToBeDrawn.get()));
            //canvas.heightProperty().addListener(event -> addImageToCanvas(imageToBeDrawn.get()));



        }


    }*/




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




        /*Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (true) {
                    //synchronized (data) {
                    System.out.println(data.isNewMessage());
                    System.out.println("is canvas null: ");
                    System.out.println(pCanvas == null);
                    if (data.isNewMessage()) {
                        data.setNewMessage(false);
                        System.out.println("got to this function");
                        //if (this.canvas != null) {
                        System.out.println("hello");
                        //Platform.runLater(() -> incoming.setText(incoming.getText() + data.getMessage()));
                        //System.out.println(context != null);
                        restartEverything(new Image(new ByteArrayInputStream(data.getArray())));
                        System.out.println(data.getArray());
                        System.out.println(pCanvas.getHeight());
                        System.out.println("image drawn");
                    }
                    //System.out.println(data.getImage() == null);

                    //}

                    System.out.println("update");
                    Thread.sleep(5000);
                }
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();*/



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





    /*public void getDrawing() {
        //System.out.println(this.imgArray);
        System.out.println(data == null);
        System.out.println(this.data.getArray());
        Platform.runLater(() -> pCanvas.getGraphicsContext2D().drawImage(new Image(new ByteArrayInputStream(this.data.getArray())), 10, 10, 20, 20));

    }*/

    public void restartEverything(byte[] b) {
        System.out.println("restarting... everything");
        Canvas c = new Canvas();


        Platform.runLater(() -> {
                pCanvas.getGraphicsContext2D().beginPath();
            pCanvas.getGraphicsContext2D().fillRoundRect(110, 60, 30, 30, 10, 10);
            pCanvas.getGraphicsContext2D().closePath();});




       /*
        GraphicsContext graphicsContext = pCanvas.getGraphicsContext2D();
        toAttach.getChildren().add(pCanvas);
        pCanvas.setHeight(400);
        pCanvas.setWidth(400);
        pCanvas.setStyle("-fx-border-color: red");
        Platform.runLater(() -> pCanvas.getGraphicsContext2D().drawImage(img, 5, 5, 200, 200));

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
                });*/


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



        //} else if (canvas != null) {



            /*Task task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    while (true) {
                        //synchronized (data) {
                        System.out.println(data.isNewMessage());
                        System.out.println("is canvas null: ");
                        System.out.println(canvas == null);
                        if (data.isNewMessage()) {
                            data.setNewMessage(false);
                            System.out.println("got to this function");
                            //if (this.canvas != null) {
                            System.out.println("hello");
                            //Platform.runLater(() -> incoming.setText(incoming.getText() + data.getMessage()));
                            //System.out.println(context != null);
                            System.out.println(canvas.getHeight());
                            canvas.getGraphicsContext2D().drawImage(new Image(new ByteArrayInputStream(data.getArray())), 0, 0, canvas.getWidth(), canvas.getHeight());
                            System.out.println("image drawn");
                        }
                        //System.out.println(data.getImage() == null);

                        //}
                        Thread.sleep(10000);
                        System.out.println("update");
                    }
                }
            };
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
            //canvas.widthProperty().addListener(event -> addImageToCanvas(imageToBeDrawn.get()));
            //canvas.heightProperty().addListener(event -> addImageToCanvas(imageToBeDrawn.get()));*/


    }
}



