import javafx.scene.canvas.Canvas;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Tests {

    @Test
    public void testCycle() {
        PeerData data = new PeerData("a", new Peer());
        data.addToQueue("b");
        data.addToQueue("c");
        ArrayList<String> test = new ArrayList<>();
        test.add("a");
        test.add("b");
        test.add("c");
        Assert.assertEquals(data.getPeers(), test);
        Assert.assertEquals(data.cyclePeers(), "b");
    }

    @Test
    public void testUpdate() {
        PeerData data = new PeerData("a", new Peer());
        data.addToQueue("b");
        data.addToQueue("c");
        data.addToQueue("d");
        ArrayList<String> test = new ArrayList<>();
        test.add("a");
        test.add("b");
        test.add("c");
        test.add("d");
        Assert.assertEquals(data.getPeers(), test);
    }

    public void selfConnect(PeerServer server, PeerClient client) {
        try {
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientServerConnection() throws IOException {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Peer peer = new Peer();
        PeerData data = new PeerData(ip, peer);
        PeerClient client = new PeerClient(ip, 5000);
        PeerServer server = new PeerServer(5000, data);
        selfConnect(server, client);
        Assert.assertTrue(client.isConnected());
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.close();
        System.out.println("connection closed");
        Assert.assertFalse(client.isConnected());
    }

    @Test
    public void testSendData() throws IOException {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Peer peer = new Peer();
        PeerClient client = new PeerClient(ip, 5000);
        PeerServer server = new PeerServer(5000, peer.data);
        selfConnect(server, client);
        Assert.assertEquals(peer.data.getPeers().get(0), ip);
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.close();
    }

    // Would love to do a test here but the logic is on an FXML thread
    //    @Test
    //    public void testSaveImage() {
    //        Peer peer = new Peer();
    //        peer.pCanvas = new Canvas();
    //        peer.pCanvas.setWidth(50);
    //        peer.pCanvas.setHeight(50);
    //        peer.pCanvas.getGraphicsContext2D().lineTo(3,5);
    //        peer.saveDrawing();
    //    }


}
