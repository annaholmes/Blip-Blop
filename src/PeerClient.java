import ocsf.client.AbstractClient;

public class PeerClient extends AbstractClient {

    private String host;
    private int port;

    public PeerClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object o) {

    }




}
