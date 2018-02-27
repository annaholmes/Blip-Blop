
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Peer {

    private static int port = 3000;
    private Socket socket;

    public static void main(String[] args) throws IOException {
        //Peer p = new Peer(Integer.parseInt(args[0]));
        Peer p = new Peer(port);
        p.listen();
    }

    private boolean quit = false;

    private ServerSocket accepter;

    public Peer(int port) throws IOException {
        accepter = new ServerSocket(port);
        System.out.println("Server: IP address: " + accepter.getInetAddress() + " (" + port + ")");
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
