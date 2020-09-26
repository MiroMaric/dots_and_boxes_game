package rs.miromaric.dotsandboxes.client.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import rs.miromaric.dotsandboxes.client.communication.Controller;
import rs.miromaric.dotsandboxes.client.forms.main.GameStage;
import rs.miromaric.dotsandboxes.client.forms.squere.Edge;
import rs.miromaric.dotsandboxes.client.forms.squere.Played;
import rs.miromaric.dotsandboxes.client.forms.squere.SquerePane;
import rs.miromaric.dotsandboxes.common.domain.Game;
import rs.miromaric.dotsandboxes.common.domain.Move;

/**
 *
 * @author miro
 */
public class Scoreboard {

    private final List<Edge> humanMoves = new ArrayList<>();
    private final Edge[][] edges;
    private final SquerePane squerePane;
    private Game game;

    public static Scoreboard instance;

    public Scoreboard(Game game, List<Edge> edges, SquerePane squerePane) {
        this.game = game;
        this.edges = getEdgesLikeArray(edges);
        this.squerePane = squerePane;
        addEdgeListeners();
        playComputerFirstMove();

    }

    public synchronized static void startGame(Game game, List<Edge> edges, SquerePane squerePane) {
        instance = new Scoreboard(game, edges, squerePane);
    }

    public static Scoreboard getInstance() {
        if (instance == null) {
            throw new RuntimeException("game is not started!");
        }
        return instance;
    }

    private Edge[][] getEdgesLikeArray(List<Edge> edges) {
        Edge[][] edgesArray = new Edge[2][game.getDimension() * (game.getDimension() - 1)];
        edges.forEach(edge -> {
            edgesArray[edge.isHorisontal() ? 0 : 1][edge.getPosition()] = edge;
        });
        return edgesArray;
    }

    private void addEdgeListeners() {
        edgesStream().forEach(edge -> {
            edge.getShape().setOnMouseClicked(event -> {
                try {
                    Move move = new Move(getMoves(edge), getSquere());
                    Integer closeMove = Controller.getInstance().isCloseMove(move);
                    System.out.println(closeMove);
                    humanMoves.add(edge);
                    boldHumanMoveEdge(edge);
                    if (closeMove >= 0) {
                        game.setHumanScore(game.getHumanScore() + 1);
                        squerePane.closeBox(edge, closeMove);
                    } else {
                        move.setMoves(getMoves(humanMoves.stream().toArray(Edge[]::new)));
                        humanMoves.clear();
                        Move humanMove = saveHumanMove(move);
                        Move computerMove = nextComputerMove(new Move(game.getId(), getSquere()));
                        closeComputerBoxes(computerMove);
                    }

                    checkEndGame();

                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
                    ex.printStackTrace();
                }
            });
        });
    }
    
    private void playComputerFirstMove() {
        try {
            if (!game.getFirstMove()) {
                Move computerMove = nextComputerMove(new Move(game.getId(), getSquere()));
                closeComputerBoxes(computerMove);
            }
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
            ex.printStackTrace();
        }
    }

    public void surrenderGame() {
        try {
            game.setComputerScore((game.getDimension() - 1) * (game.getDimension() - 1));
            game.setHumanScore(0);
            endGame();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public Game getGame() {
        return game;
    }

    public Edge[][] getEdges() {
        return edges;
    }

    public Stream<Edge> edgesStream() {
        return Arrays.stream(edges)
                .flatMap(Arrays::stream);
    }

    public Integer[][] getMoves(Edge... edges) {
        Integer[][] moves = new Integer[edges.length][2];
        for (int i = 0; i < edges.length; i++) {
            moves[i][0] = edges[i].isHorisontal() ? 0 : 1;
            moves[i][1] = edges[i].getPosition();
        }
        return moves;
    }

    public Boolean[][] getSquere() {
        Boolean[][] squere = new Boolean[2][game.getDimension() * (game.getDimension() - 1)];
        edgesStream().forEach(e
                -> squere[e.isHorisontal() ? 0 : 1][e.getPosition()] = e.getPlayed() != Played.NONE);
        return squere;
    }

    private Move nextComputerMove(Move move) throws Exception {
        Move computerMove = Controller.getInstance().nextComputerMove(move);
        System.out.println(computerMove);
        return computerMove;
    }

    private Move saveHumanMove(Move move) throws Exception {
        move.setGameId(game.getId());
        return Controller.getInstance().saveHumanMove(move);
    }

    private void boldHumanMoveEdge(Edge edge) {
        edge.setPlayed(Played.PLAYER);
    }

    private void checkEndGame() throws Exception {
        if (edgesStream().allMatch(edge -> edge.getPlayed() != Played.NONE)) {
            endGame();
        }
    }
    
    private void closeComputerBoxes(Move computerMove) throws Exception {
        Boolean[][] squere = getSquere();
        for (Integer[] move : computerMove.getMoves()) {
            edges[move[0]][move[1]].setPlayed(Played.COMPUTER);
            Integer closeBoxPosition = Controller.getInstance().isCloseMove(new Move(new Integer[][]{move}, squere));
            System.out.println(closeBoxPosition);
            if (closeBoxPosition >= 0) {
                game.setComputerScore(game.getComputerScore() + 1);
                squerePane.closeBox(edges[move[0]][move[1]], closeBoxPosition);
            }
            squere[move[0]][move[1]] = true;
        }
    }

    private void endGame() throws Exception {
        this.game = Controller.getInstance().endGame(game);
        GameStage.getInstance().setScene("forms/home/FXMLHomePane.fxml");
        getEndgameAlert().show();
    }

    private String getEndGameMessage() {
        String endGameText = "";
        endGameText += "\nComputer: " + game.getComputerScore();
        endGameText += "\nYou: " + game.getHumanScore();
        return endGameText;
    }

    private String getEndGameMessageHeader() {
        String header = "";
        if (game.getHumanScore() > game.getComputerScore()) {
            header += "YOU WIN!";
        } else if (Objects.equals(game.getHumanScore(), game.getComputerScore())) {
            header += "DRAW!";
        } else {
            header += "COMPUTER WIN!";
        }
        return header;
    }

    private Alert getEndgameAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SCOREBOARD");
        alert.setContentText(getEndGameMessage());
        alert.setHeaderText(getEndGameMessageHeader());
        return alert;
    }

}
