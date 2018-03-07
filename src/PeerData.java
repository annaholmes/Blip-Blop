import java.util.ArrayList;

public class PeerData {
    private ArrayList<String> peers;
    private ArrayList<String> toQueue;

    public boolean newMessage;
    private Integer message;

    public PeerData(String ip) {
        this.peers = new ArrayList<>();
        peers.add(ip);
        this.toQueue = new ArrayList<>();
        this.newMessage = false;

    }

    public synchronized void addToQueue(String peer) {

        System.out.println(peer);
        toQueue.add(peer);
    }

    public synchronized ArrayList<String> getPeers() {
        updatePeers();
        return peers;
    }

    private void updatePeers() {
        peers.addAll(toQueue);
        toQueue.clear();
    }

    public synchronized String cyclePeers() {
        updatePeers();
        String peer = peers.remove(0);
        peers.add(peer);
        System.out.println(peers);
        return peer;
    }

    public synchronized ArrayList<String> getToQueue() {
        return toQueue;
    }

    public synchronized boolean isNewMessage() {
        return newMessage;
    }

    public synchronized Integer getMessage() {
        return message;
    }

    public synchronized void setPeers(ArrayList<String> peers) {
        this.peers = peers;
    }

    public synchronized void setToQueue(ArrayList<String> toQueue) {
        this.toQueue = toQueue;
    }

    public synchronized void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    public synchronized void setMessage(Integer message) {
        System.out.println("here it is! " + message);
        this.message = message;
    }
}
