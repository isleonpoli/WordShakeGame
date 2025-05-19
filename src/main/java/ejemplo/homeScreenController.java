package ejemplo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class homeScreenController implements Initializable {

 

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private ImageView homeScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File homeImageFile = new File("Pantallas/home.png");
        Image homeScreenImage = new Image(homeImageFile.toURI().toString());
        homeScreen.setImage(homeScreenImage);
    }    



    @FXML
    void signInButtonOnAction(ActionEvent event) {
        sceneManager.changeScene("inicioSesion.fxml");
    }

    @FXML
    void signUpButtonOnAction(ActionEvent event) {
        sceneManager.changeScene("signUpScreen.fxml");
    }

}
