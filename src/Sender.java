import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {
	public static void send(Socket target, String message) throws IOException {
		PrintWriter sockout = new PrintWriter(target.getOutputStream());
		sockout.println(message);
		sockout.flush();		
	}
}
