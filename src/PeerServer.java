import javafx.scene.shape.Path;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
        } /*else if (o instanceof Integer) {
            //synchronized (data) {
            data.setNewMessage(true);

            System.out.println(o);
            System.out.println(data.getMessage());
            data.setMessage((Integer) o);

        }*/ else if (o instanceof byte[]) {
            data.setNewMessage(true);
            System.out.println("hello it's a byte array");
            data.setImage((byte[]) o);

            //this.peer.setImgArray((byte[]) o);
            //System.out.println(data.getMessage());

        } else if (o instanceof Integer) {
            System.out.println("Hi");
            data.setMessage((Integer) o);
            System.out.println(data.getMessage());
        }
        else {
            System.out.println("whoop!");
        }


    }





}
