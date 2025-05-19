package ejemplo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

public class rulesScreenController implements Initializable{

    @FXML
    private ImageView rulesView;

    @FXML
    private Button backbtn;

    

    @FXML
    void onBtnBackAction(ActionEvent event) {
        sceneManager.changeScene("profileScreen.fxml", true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File ruleImageFile = new File("Pantallas/rules.png");
        Image ruleScreenImage = new Image(ruleImageFile.toURI().toString());
        rulesView.setImage(ruleScreenImage);
    }  
}
