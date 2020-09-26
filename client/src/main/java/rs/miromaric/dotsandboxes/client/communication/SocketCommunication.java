package rs.miromaric.dotsandboxes.client.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rs.miromaric.dotsandboxes.client.settings.AppSettings;
import rs.miromaric.dotsandboxes.common.request.Request;
import rs.miromaric.dotsandboxes.common.response.Response;

/**
 *
 * @author miro
 */
public class SocketCommunication {

    private final Socket socket;

    public SocketCommunication() {
        try {
            socket = new Socket(
                    AppSettings.getInstance().getProperty("server.socket.host"),
                    Integer.parseInt(AppSettings.getInstance().getProperty("server.socket.port")));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendRequest(Request request)throws Exception{
        try{
            ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
            outSocket.writeObject(request);
        } catch (IOException ex) {
            System.out.println(ex);
            throw new IOException("Communication error");
        }
    }

    public Response readResponse()throws Exception{
        try{
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            return (Response) inSocket.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
            throw new IOException("Communication error");
        }
    }

}
