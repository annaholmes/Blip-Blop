import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.util.ArrayList;
import java.util.Queue;

public class PeerServer extends AbstractServer {

    private ArrayList<String> peers;
    private ArrayList<String> toQueue;

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    public boolean newMessage;
    private int message;

    public PeerServer(int port) {
        super(port);
        this.newMessage = false;
        this.peers = new ArrayList<>();
        this.toQueue = new ArrayList<>();
        this.message = 0;
    }

    @Override
    protected void handleMessageFromClient(Object o, ConnectionToClient connectionToClient) {
        // get message
        // if arg0 is instanceOf queue
            // handle queue
        System.out.println("message recieved");

        if (o instanceof String) {
            System.out.println(o);
            addToQueue((String) o);
        }
        else if (o instanceof ArrayList) {
            peers = (ArrayList<String>) o;
        } else if (o instanceof Integer) {
            newMessage = true;
            message = (Integer) o;
            System.out.println(o);
        }
        else {
            System.out.println("whoop!");
        }


    }



    public int getMessage() {
        return message;
    }


    private void addToQueue(String peer) {
        toQueue.add(peer);
    }

    public ArrayList<String> getQueue() {
        peers.addAll(toQueue);
        toQueue.clear();

        return peers;
    }




}
