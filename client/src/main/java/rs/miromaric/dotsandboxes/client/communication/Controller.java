package rs.miromaric.dotsandboxes.client.communication;

import java.util.List;
import rs.miromaric.dotsandboxes.client.settings.AppSettings;
import rs.miromaric.dotsandboxes.common.domain.Game;
import rs.miromaric.dotsandboxes.common.domain.Move;
import rs.miromaric.dotsandboxes.common.domain.Player;
import rs.miromaric.dotsandboxes.common.request.Request;
import rs.miromaric.dotsandboxes.common.request.RequestOperation;
import rs.miromaric.dotsandboxes.common.response.Response;
import rs.miromaric.dotsandboxes.common.response.ResponseStatus;

/**
 *
 * @author miro
 */
public class Controller {

    private static Controller instance;
    private final WebCommunication webCommunication;
    private final SocketCommunication socketCommunication;
    
    private Controller() {
        webCommunication = new WebCommunication();
        socketCommunication = new SocketCommunication();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Player logIn(String nickaname, String password) throws Exception {
        Player player = new Player(-1L, nickaname, password);
        Request request = new Request(RequestOperation.LOGIN, player);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Player) response.getPayload();
        }
        throw response.getException();
    }
    
    public Player signup(String nickaname, String password) throws Exception {
        Player player = new Player(-1L, nickaname, password);
        Request request = new Request(RequestOperation.SIGNUP, player);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Player) response.getPayload();
        }
        throw response.getException();
    }
    
    public Game startGame(Game game) throws Exception {
        Request request = new Request(RequestOperation.START_GAME, game);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Game) response.getPayload();
        }
        throw response.getException();
    }
    
    public Integer isCloseMove(Move move) throws Exception {
        return webCommunication.post(move, Integer.class, AppSettings.getInstance().getProperty("api.web.endpoint.isclosemove"));
    }
    
    public Move nextComputerMove(Move move) throws Exception {
        return webCommunication.post(move, Move.class, AppSettings.getInstance().getProperty("api.web.endpoint.nextmove"));
    }
    
    public Move saveHumanMove(Move move) throws Exception {
        Request request = new Request(RequestOperation.SAVE_MOVE, move);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Move) response.getPayload();
        }
        throw response.getException();
    }
    
    public Game endGame(Game game) throws Exception {
        Request request = new Request(RequestOperation.END_GAME, game);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Game) response.getPayload();
        }
        throw response.getException();
    }
    
    public List<Game> findScores(Game game) throws Exception {
        Request request = new Request(RequestOperation.FIND_SCORES, game);
        socketCommunication.sendRequest(request);
        Response response = socketCommunication.readResponse();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (List<Game>)response.getPayload();
        }
        throw response.getException();
    }

}
