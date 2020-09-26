package rs.miromaric.dotsandboxes.client.forms.startgame;

import java.net.URL;
import java.util.Date;
import java.util.EnumSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import rs.miromaric.dotsandboxes.client.PlayerType;
import rs.miromaric.dotsandboxes.client.communication.Controller;
import rs.miromaric.dotsandboxes.client.forms.main.GameStage;
import rs.miromaric.dotsandboxes.client.forms.squere.SquerePane;
import rs.miromaric.dotsandboxes.client.game.Scoreboard;
import rs.miromaric.dotsandboxes.client.game.Session;
import rs.miromaric.dotsandboxes.common.domain.Game;

/**
 *
 * @author miro
 */
public class StartGamePaneController implements Initializable {

    @FXML
    private ChoiceBox cbFirstMove;

    @FXML
    private ChoiceBox cbSize;

    @FXML
    private Label lblFirstMove;

    @FXML
    private Label lblSize;

    @FXML
    private Label lblInfo;

    @FXML
    private Button btnStartGame;

    @FXML
    private Button btnCancel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateForm();
        addStartGameListener();
        addCancelListener();
    }

    private void populateForm() {
        EnumSet.allOf(PlayerType.class).stream()
                .forEach(cbFirstMove.getItems()::add);
        cbFirstMove.setValue(PlayerType.HUMAN);

        cbSize.getItems().addAll(3, 4, 5, 6, 7, 8, 8, 10);
        cbSize.setValue(5);
    }

    private void addStartGameListener() {
        btnStartGame.setOnMouseClicked((event) -> {
            final PlayerType firstMove = (PlayerType) cbFirstMove.getValue();
            final int dimension = Integer.parseInt(cbSize.getValue().toString());
            try {
                Game game = Controller.getInstance().startGame(new Game(Session.getInstance().getPlayer().getId(),
                                                                        new Date(),
                                                                        firstMove == PlayerType.HUMAN,
                                                                        dimension));
                SquerePane squerePane = new SquerePane(game);
                GameStage.getInstance().setScene(new Scene(squerePane));
            } catch (Exception ex) {
                System.out.println(ex);
                lblInfo.setText(ex.getMessage());
            }
        });
    }
    
    private void addCancelListener() {
        btnCancel.setOnMouseClicked((event) -> {
            GameStage.getInstance().setScene("forms/home/FXMLHomePane.fxml");
        });
    }

}
