package rs.miromaric.dotsandboxes.server.start;

import java.net.ServerSocket;
import java.net.Socket;
import rs.miromaric.dotsandboxes.server.client.ClientThread;

/**
 *
 * @author miro
 */
public class SocketCommunication {
    private final boolean active = true;

    public void startServer() throws Exception {
        Class.forName("rs.miromaric.dotsandboxes.server.operations.OperationFinder");
        ServerSocket ss = new ServerSocket(9004);
        System.out.println("Server is up and runnig");
        while (active) {
            Socket socket = ss.accept();
            System.out.println("Accepted");
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
        }
    }
}
