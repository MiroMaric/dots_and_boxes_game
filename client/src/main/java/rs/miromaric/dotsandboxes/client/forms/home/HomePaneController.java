package rs.miromaric.dotsandboxes.client.forms.home;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import rs.miromaric.dotsandboxes.client.forms.main.GameStage;
import rs.miromaric.dotsandboxes.client.settings.AppSettings;

/**
 *
 * @author miro
 */
public class HomePaneController implements Initializable {
    
    @FXML
    private Button btnGame;
    @FXML
    private Button btnScores;
    @FXML
    private Button btnAbout;
    @FXML
    private Button btnLogout;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnGame.setOnMouseClicked((event) -> {
            GameStage.getInstance().setScene("forms/startgame/FXMLStartGamePane.fxml");
        });
        
        btnScores.setOnMouseClicked(event -> showScores());
        
        btnAbout.setOnMouseClicked(event -> showAbout());
        
        btnLogout.setOnMouseClicked(event -> logout());
    }
    
    public void showScores() {
        GameStage.getInstance().setScene("forms/score/FXMLScorePane.fxml");
    }
    
    public void showAbout() {
        getAboutAlert().show();
    }
    
    public void logout() {
        GameStage.getInstance().setScene("forms/login/FXMLLoginPane.fxml");
    }

    private Alert getAboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(AppSettings.getInstance().getProperty("about.header"));
        alert.setContentText(AppSettings.getInstance().getProperty("about.content"));
        return alert;
    }
    
}
