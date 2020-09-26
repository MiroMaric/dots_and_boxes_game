package rs.miromaric.dotsandboxes.server.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rs.miromaric.dotsandboxes.common.domain.Game;
import rs.miromaric.dotsandboxes.common.domain.GeneralEntity;
import rs.miromaric.dotsandboxes.common.domain.Move;
import rs.miromaric.dotsandboxes.common.domain.Player;
import rs.miromaric.dotsandboxes.common.request.Request;
import rs.miromaric.dotsandboxes.common.response.Response;
import rs.miromaric.dotsandboxes.common.response.ResponseStatus;
import rs.miromaric.dotsandboxes.server.operations.GenericOperation;
import rs.miromaric.dotsandboxes.server.operations.OperationFinder;
import rs.miromaric.dotsandboxes.server.operations.impl.EndGameOperation;
import rs.miromaric.dotsandboxes.server.operations.impl.FindScoresOperation;
import rs.miromaric.dotsandboxes.server.operations.impl.LoginOperation;
import rs.miromaric.dotsandboxes.server.operations.impl.SaveMoveOperation;
import rs.miromaric.dotsandboxes.server.operations.impl.SignupOperation;
import rs.miromaric.dotsandboxes.server.operations.impl.StartGameOperation;

/**
 *
 * @author miro
 */
public class ClientThread extends Thread {
    
    private final Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handleRequest();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    private void handleRequest() throws IOException, ClassNotFoundException {
        while (!isInterrupted()) {
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            Request request = (Request) inSocket.readObject();
            Response response = new Response();
            try {
                System.out.println("Operation: " + request.getOperation());
                
                GenericOperation operation = OperationFinder.findOperation(request.getOperation());
                operation.templateExecute(request.getData());
                response.setStatus(ResponseStatus.SUCCESS);
                response.setPayload(operation.getResult());
                
            } catch (Exception ex) {
                ex.printStackTrace();
                response.setStatus(ResponseStatus.ERROR);
                response.setException(ex);
            }
            
            sendResponse(response);
        }
    }

    private void sendResponse(Response response) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(response);
    }
}
