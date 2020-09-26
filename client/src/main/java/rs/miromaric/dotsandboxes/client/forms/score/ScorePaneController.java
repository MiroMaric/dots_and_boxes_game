package rs.miromaric.dotsandboxes.client.forms.score;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rs.miromaric.dotsandboxes.client.communication.Controller;
import rs.miromaric.dotsandboxes.client.forms.main.GameStage;
import rs.miromaric.dotsandboxes.client.game.Session;
import rs.miromaric.dotsandboxes.common.domain.Game;

/**
 *
 * @author miro
 */
public class ScorePaneController implements Initializable {

    @FXML
    private TableView tblScore;

    @FXML
    private ChoiceBox<Integer> cbSize;

    @FXML
    private Button btnSearch;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private Label lblSize;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepereScoreViewTable();
        prepereSizeBox();
        
        btnSearch.setOnMouseClicked((event) -> {
            try {
                int size = cbSize.getValue();
                
                List<Game> scores = 
                        Controller.getInstance()
                                .findScores(new Game(Session.getInstance().getPlayer().getId(), size))
                                .stream()
                                .sorted(Comparator.comparingInt(Game::getHumanScore).reversed())
                                .collect(Collectors.toList());
                System.out.println("score:");
                scores.forEach(System.out::println);
                tblScore.getItems().clear();
                tblScore.getItems().addAll(scores);
            } catch (Exception ex) {
                Logger.getLogger(ScorePaneController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
            }
        });
        
        btnCancel.setOnMouseClicked((event) -> {
            GameStage.getInstance().setScene("forms/home/FXMLHomePane.fxml");
        });
        
    }

    private void prepereScoreViewTable() {
        tblScore.getColumns().clear();
        
        TableColumn<String, Game> humanScore = new TableColumn<>("Your Score");
        TableColumn<String, Game> computerScore = new TableColumn<>("Computer Score");
        
        humanScore.setPrefWidth(160);
        computerScore.setPrefWidth(160);
        
        humanScore.setCellValueFactory(new PropertyValueFactory<>("humanScore"));
        computerScore.setCellValueFactory(new PropertyValueFactory<>("computerScore"));
        
        tblScore.getColumns().add(humanScore);
        tblScore.getColumns().add(computerScore);
    }

    private void prepereSizeBox() {
        cbSize.getItems().addAll(3, 4, 5, 6, 7, 8, 8, 10);
        cbSize.setValue(5);
    }

}
