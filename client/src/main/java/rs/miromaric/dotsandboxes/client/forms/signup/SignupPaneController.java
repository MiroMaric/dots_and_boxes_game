package rs.miromaric.dotsandboxes.client.forms.signup;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rs.miromaric.dotsandboxes.client.communication.Controller;
import rs.miromaric.dotsandboxes.client.forms.main.GameStage;

/**
 *
 * @author miro
 */
public class SignupPaneController implements Initializable {

    @FXML
    private TextField tfNickname;

    @FXML
    private PasswordField pfPassword;
    
    @FXML
    private PasswordField pfRepeatPassword;

    @FXML
    private Button btnSignup;
    
    @FXML
    private Button btnCancel;

    @FXML
    private Label lblNickname;

    @FXML
    private Label lblPassword;
    
    @FXML
    private Label lblRepeatPassword;
    
    @FXML
    private Label lblInfo;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSignup.setOnMouseClicked(event -> {
            
            String nickname = tfNickname.getText();
            String password = pfPassword.getText();
            String repeatPassword = pfRepeatPassword.getText();
            
            if(!password.equals(repeatPassword)) {
                lblInfo.setText("Password mismatch");
            } else {
                try {
                    Controller.getInstance().signup(nickname, password);
                    GameStage.getInstance().setScene("forms/login/FXMLLoginPane.fxml");
                    new Alert(Alert.AlertType.INFORMATION, "Success", ButtonType.OK).show();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    lblInfo.setText(ex.getMessage());
                }
                
            }
            
        });
        
        btnCancel.setOnMouseClicked(event -> GameStage.getInstance().setScene("forms/login/FXMLLoginPane.fxml"));
        
    }

}
