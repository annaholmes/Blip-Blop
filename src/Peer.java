
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Peer {

    private static int port = 3000;
    private Socket socket;

    private String ip = "209.65.56.40";
    
    public static void main(String[] args) throws IOException {
        //Peer p = new Peer(Integer.parseInt(args[0]));
        Peer p = new Peer(port);
        p.listen();
    }

    private boolean quit = false;

    private ServerSocket accepter;

    public Peer(int port) throws IOException {
        accepter = new ServerSocket(port);
        System.out.println("Server: IP address: " + ip + " (" + port + ")");
    }

    public void quit() {
        quit = true;
    }

    public void listen() throws IOException {
        while (!quit) {
            this.socket = accepter.accept();
            System.out.println("Server: Connection accepted from " + socket.getInetAddress());

        }
    }







}
