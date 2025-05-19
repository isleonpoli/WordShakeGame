package ejemplo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.util.ResourceBundle;


import conexion.conexionSQL;

public class signinController implements Initializable {

    @FXML ImageView signinView;
    @FXML ImageView iconSignin;
 
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("Pantallas/signin.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        signinView.setImage(brandingImage);

        File iconFile = new File("Pantallas/iconsignin.png");
        Image iconImage = new Image(iconFile.toURI().toString());
        iconSignin.setImage(iconImage);

    }

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label signinMessageLabel;

    @FXML
    private Button singinButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    void cancelButtonOnAction(ActionEvent event) throws IOException{
        sceneManager.changeScene("homeScreen.fxml");
    }

    @FXML
    void signinButtonOnAction(ActionEvent event) {
        
        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            validatesignin();    
        }else{
            signinMessageLabel.setText("Please enter your username and password");
        }
    }

    public void validatesignin (){
        // Probar la conexión a la base de datos y realizar una consulta
          try (Connection conn = conexionSQL.getConnection()) {
            // Consulta SQL para obtener todas las palabras y su puntaje
            String verifySignIn = "SELECT count(1) FROM users where username = '" + usernameTextField.getText() + "' AND password_user = '" + passwordTextField.getText() + "'";
            
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(verifySignIn)) {
                System.out.println("Conexión establecida. Resultado de la consulta:");

                // Mostrar los resultados
                while (rs.next()) {
                    if(rs.getInt(1) == 1){
                        session.setUsername(usernameTextField.getText());
                        sceneManager.changeScene("profileScreen.fxml", true);
                    }else{
                        signinMessageLabel.setText("Invalid login. Try again");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error ejecutando la consulta.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos.");
            e.printStackTrace();
        }
    }
    
}
