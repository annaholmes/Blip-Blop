
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Peer1 {

    private static int port = 3000;
    private String incomingMessage;
    private Socket socket;
    private Socket s = new Socket();

    private String ip;
    private String target;
    
    public static void main(String[] args) throws IOException {
        //Peer p = new Peer(Integer.parseInt(args[0]));
        Peer1 p = new Peer1(port);
        p.listen();
    }



    private boolean quit = false;

    private ServerSocket accepter;

    public Peer1(int port) throws IOException {

        this.ip = InetAddress.getLocalHost().getHostAddress();
        accepter = new ServerSocket(port);
        this.target = "none";
        System.out.println("Server: IP address: " + ip + " (" + port + ")");
        // set up own serversocket
        // listen on server for clients
        //      when client pings
        //          send string message

        // when button pressed w ip entered -
        //      open socket w ip + port
        //      ping & recieve string message
        //          print message

    }

    public void quit() {
        quit = true;
    }

    public void listen() throws IOException {
        while (!quit) {
            this.socket = accepter.accept();
            System.out.println("Server: Connection accepted from " + socket.getInetAddress());
            target = this.socket.getInetAddress().getHostAddress();
            if (!this.socket.equals("none")) {
                quit();
            }
        }
        send();
    }

    public void sendTo(String host, int port, String message) {
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

    void send() {
        sendTo(target, this.port, "404");
    }

    void send(Socket target, String message) throws IOException {
        PrintWriter sockout = new PrintWriter(target.getOutputStream());
        sockout.println(message);
        System.out.println("hey");
        sockout.flush();
    }

    void receive(Socket target) throws IOException {
        BufferedReader sockin = new BufferedReader(new InputStreamReader(target.getInputStream()));
        while (!sockin.ready()) {}
        while (sockin.ready()) {
            try {
                String msg = sockin.readLine();
                Platform.runLater(() -> {incomingMessage = incomingMessage + '\n' + msg;});
            } catch (Exception e) {
                Platform.runLater(() -> badNews(e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    void badNews(String what) {
        Alert badNum = new Alert(Alert.AlertType.ERROR);
        badNum.setContentText(what);
        badNum.show();
    }

}
