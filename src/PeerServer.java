import javafx.application.Platform;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class PeerServer extends AbstractServer {



    public PeerData data;


    public PeerServer(int port, PeerData data) {
        super(port);

        this.data = data;
    }

    @Override
    protected void handleMessageFromClient(Object o, ConnectionToClient connectionToClient) {
        // get message
        // if arg0 is instanceOf queue
            // handle queue
        System.out.println("message received");

        if (o instanceof String) {
            System.out.println(o);
            //synchronized (data) {
            data.addToQueue((String) o);
            //}
        }
        else if (o instanceof ArrayList) {
            //synchronized (data) {
                data.setPeers((ArrayList<String>) o);
            //}
        } else if (o instanceof Integer) {
            //synchronized (data) {
            data.setNewMessage(true);
            data.setMessage((Integer) o);
            //}
            System.out.println(o);
            System.out.println(data.getMessage());

        }
        else {
            System.out.println("whoop!");
        }


    }





}
