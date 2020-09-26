package rs.miromaric.dotsandboxes.client.forms.main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author miro
 */
public class DotsAndBoxes extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Dots And Boxes");
        stage.setResizable(false);
        GameStage.setStage(stage);
        stage.show();
        GameStage.getInstance().setScene("forms/login/FXMLLoginPane.fxml");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
