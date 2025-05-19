package ejemplo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class requirementsScreenController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ImageView requirementsView;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File requirementsImageFile = new File("Pantallas/question.png");
        Image requirementsScreenImage = new Image(requirementsImageFile.toURI().toString());
        requirementsView.setImage(requirementsScreenImage);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        sceneManager.changeScene("signUpScreen.fxml");
    }

}
