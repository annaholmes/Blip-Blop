import java.io.*;
import java.net.*;

public class Peer2 {

    private int port;
    private String local;
    private String other;
    private ServerSocket serverSocket;
    private Socket connectionSocket;

    private String peerMessage;
    private Socket socket;
    private PrintWriter socketOut;
    private String requestMessage;

    public Peer2(int port) throws IOException {
        this.port = port;


    }

    public void launchServer() {
        PrintStream out = null;
        BufferedReader in = null;
        try {
            this.serverSocket = new ServerSocket(port, 3);
            this.connectionSocket = serverSocket.accept();
            System.out.println(connectionSocket.getInetAddress().getHostName() + " : " + connectionSocket.getPort());
            out = new PrintStream(connectionSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            do
            {
                //read input from client
                peerMessage = (String)in.readLine();
                System.out.println("client>" + peerMessage);

                if(peerMessage != null)
                {
                    out.println(peerMessage);
                }
                else
                {
                    System.out.println("Client has disconnected");
                    break;
                }
            }
            while(!peerMessage.equals("bye"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void listen() {
        // get text from listening
        // set peer text to heard text
    }

    public void connectToServer(String hostIP) {
        try {
            this.socket = new Socket(hostIP, port);
            System.out.println("connected to server: " + this.socket.getInetAddress());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(String message) {
        try {
            this.socketOut = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketOut.println(message);
        System.out.println("message sent");
    }
}
