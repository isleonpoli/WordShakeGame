package ejemplo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import conexion.conexionSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class profileScreenController implements Initializable {
    
    @FXML
    private Label usernameLabel;

    
    @FXML
    private Button rulesButton;


    @FXML
    private ImageView profileView;


    @FXML
    private Label bestScoreLabel;


    @FXML
    private Button playButton;

    @FXML
    private TableView<topUser> tabTopUsers;

    @FXML
    private TableColumn<topUser, String> UsersCol;

    @FXML
    private TableColumn<topUser, Integer> bestScoreCol;

    private ObservableList<topUser> topUsersList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        
        File profileFile = new File("Pantallas/profile.png");
        Image profileImage = new Image(profileFile.toURI().toString());
        profileView.setImage(profileImage);


        // Mostrar nombre de usuario
        String username = session.getUsername();
        usernameLabel.setText("user: " + username);

        // Obtener y mostrar el mejor puntaje desde la base de datos
        String getBestScoreQuery = "SELECT score FROM users WHERE username = ?";

        try (Connection conn = conexionSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(getBestScoreQuery)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int bestScore = rs.getInt("score");
                    bestScoreLabel.setText("Best score: " + bestScore);
                } else {
                    bestScoreLabel.setText("-------");
                }
            }

        } catch (SQLException e) {
            bestScoreLabel.setText("Error al obtener puntaje");
            e.printStackTrace();
        }

        // Configurar columnas
        UsersCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        bestScoreCol.setCellValueFactory(new PropertyValueFactory<>("bestScore"));

        // Cargar top 3 puntajes
        cargarTop3Usuarios();
        
    }    

    private void cargarTop3Usuarios() {
    String sql = "SELECT username, score FROM users ORDER BY score DESC LIMIT 3";

    try (Connection conn = conexionSQL.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            String user = rs.getString("username");
            int score = rs.getInt("score");
            topUsersList.add(new topUser(user, score));
        }

        tabTopUsers.setItems(topUsersList);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void setUsername(String username) {
        usernameLabel.setText(username);
    }


    @FXML
    void playButtonOnAction(ActionEvent event) throws IOException{
        sceneManager.changeScene("playScreen.fxml");
    }


    @FXML
    void rulesButtonOnAction(ActionEvent event) throws IOException{
        sceneManager.changeScene("rulesScreen.fxml", true);
    }


}
