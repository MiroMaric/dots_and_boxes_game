package rs.miromaric.dotsandboxes.client.forms.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rs.miromaric.dotsandboxes.client.communication.Controller;
import rs.miromaric.dotsandboxes.client.forms.main.GameStage;
import rs.miromaric.dotsandboxes.client.game.Session;
import rs.miromaric.dotsandboxes.common.domain.Player;

/**
 *
 * @author miro
 */
public class LoginPaneController implements Initializable {

    @FXML
    private TextField tfNickname;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Button btnSignup;

    @FXML
    private Label lblInfo;
    
    @FXML
    private Label lblNickname;

    @FXML
    private Label lblPassword;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addLoginListener();
        addSignUpListener();
    }

    private void addLoginListener() {
        btnLogin.setOnMouseClicked((event) -> {
            String nickname = tfNickname.getText();
            String password = pfPassword.getText();
            try {
                Player player = Controller.getInstance().logIn(nickname, password);
                Session.getInstance().setPlayer(player);
                GameStage.getInstance().setScene("forms/home/FXMLHomePane.fxml");
            } catch (Exception ex) {
                System.out.println(ex);
                lblInfo.setText(ex.getMessage());
            }
        });
    }
    
    private void addSignUpListener() {
        btnSignup.setOnMouseClicked((event) -> {
            GameStage.getInstance().setScene("forms/signup/FXMLSignupPane.fxml");
        });
    }

}
