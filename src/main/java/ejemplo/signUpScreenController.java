package ejemplo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import conexion.conexionSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class signUpScreenController implements Initializable {

    @FXML private ImageView signUpView;
    @FXML private Button cancelButton;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField confirmPasswordTextField;
    @FXML private Label signUpMessageLabel;
    @FXML private Button signUpButton;
    @FXML private TextField usernameTextField;
    @FXML private TextField emailTextField;
    @FXML private Button questionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File signUpFile = new File("Pantallas/signUp.png");
        Image signUpImage = new Image(signUpFile.toURI().toString());
        signUpView.setImage(signUpImage);
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) throws IOException {
        sceneManager.changeScene("homeScreen.fxml");
    }

    @FXML
    void questionButtonOnAction(ActionEvent event) throws IOException {
        sceneManager.changeScene("requirementsScreen.fxml");
    }

    @FXML
    void signUpButtonOnAction(ActionEvent event) {
        if (!usernameTextField.getText().isBlank() && 
            !passwordTextField.getText().isBlank() && 
            !confirmPasswordTextField.getText().isBlank() &&
            !emailTextField.getText().isBlank()) {

            if (!passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
                signUpMessageLabel.setText("Passwords do not match");
                return;
            }

            if (!validarContraseña(passwordTextField.getText())) {
                signUpMessageLabel.setText("Insecure password");
                return;
            }

            if (!automataCorreo(emailTextField.getText()).equals("Correo valido")) {
                signUpMessageLabel.setText("Invalid email format");
                return;
            }

            registerNewUser();

        } else {
            signUpMessageLabel.setText("Please complete all fields");
        }
    }

    public void registerNewUser() {
        try (Connection conn = conexionSQL.getConnection()) {
            // Verificar si el username ya existe
            String checkUsernameSQL = "SELECT COUNT(1) FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkUsernameSQL)) {
                stmt.setString(1, usernameTextField.getText());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        signUpMessageLabel.setText("Username already exists");
                        return;
                    }
                }
            }

            // Verificar si el email ya existe
            String checkEmailSQL = "SELECT COUNT(1) FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkEmailSQL)) {
                stmt.setString(1, emailTextField.getText());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        signUpMessageLabel.setText("Email already exists");
                        return;
                    }
                }
            }

            // Insertar nuevo usuario
            String insertUserSQL = "INSERT INTO users (username, password_user, email) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {
                stmt.setString(1, usernameTextField.getText());
                stmt.setString(2, passwordTextField.getText());
                stmt.setString(3, emailTextField.getText());
                stmt.executeUpdate();
            }

            signUpMessageLabel.setText("User registered successfully!");
            sceneManager.changeScene("inicioSesion.fxml");

        } catch (SQLException e) {
            System.out.println("Error registering user.");
            e.printStackTrace();
        }
    }

    private boolean validarContraseña(String contraseña) {
        char caracter;
        boolean cantidad = false;
        boolean numero = false;
        boolean mayuscula = false;
        boolean minuscula = false;
        boolean caracter_especial = false;

        if (contraseña.length() >= 5) {
            cantidad = true;
        }

        String caracteres_especiales = ",.-_#*!$%&/";

        for (int i = 0; i < contraseña.length(); i++) {
            caracter = contraseña.charAt(i);

            if (Character.isDigit(caracter)) {
                numero = true;
            }
            if (Character.isUpperCase(caracter)) {
                mayuscula = true;
            }
            if (Character.isLowerCase(caracter)) {
                minuscula = true;
            }
            if (caracteres_especiales.contains(Character.toString(caracter))) {
                caracter_especial = true;
            }

            if (cantidad && numero && mayuscula && minuscula && caracter_especial) {
                return true;
            }
        }

        return false;
    }

    private String automataCorreo(String correo) {
        String estado = "q0";
        char[] letras = correo.toCharArray();

        for (int i = 0; i < letras.length; i++) {
            switch (estado) {
                case "q0":
                    if (Pattern.matches("[a-zA-Z0-9]", Character.toString(letras[i]))) estado = "q1";
                    else estado = "error";
                    break;
                case "q1":
                    if (Pattern.matches("[a-zA-Z0-9]", Character.toString(letras[i]))) estado = "q1";
                    else if (Pattern.matches("[.]", Character.toString(letras[i]))) estado = "q0";
                    else if (Pattern.matches("[@]", Character.toString(letras[i]))) estado = "q2";
                    else estado = "error";
                    break;
                case "q2":
                    if (Pattern.matches("[a-zA-Z0-9]", Character.toString(letras[i]))) estado = "q3";
                    else estado = "error";
                    break;
                case "q3":
                    if (Pattern.matches("[a-zA-Z0-9]", Character.toString(letras[i]))) estado = "q4";
                    else estado = "error";
                    break;
                case "q4":
                    if (Pattern.matches("[a-zA-Z0-9]", Character.toString(letras[i]))) estado = "q5";
                    else estado = "error";
                    break;
                case "q5":
                    if (Pattern.matches("[a-zA-Z0-9]", Character.toString(letras[i]))) estado = "q6";
                    else estado = "error";
                    break;
                case "q6":
                    if (Pattern.matches("[a-zA-Z0-9]", Character.toString(letras[i]))) estado = "q6";
                    else if (Pattern.matches("[.]", Character.toString(letras[i]))) estado = "q7";
                    else estado = "error";
                    break;
                case "q7":
                    if (Pattern.matches("[a-z]", Character.toString(letras[i]))) estado = "q8";
                    else estado = "error";
                    break;
                case "q8":
                    if (Pattern.matches("[a-z]", Character.toString(letras[i]))) estado = "q9";
                    else estado = "error";
                    break;
                case "q9":
                    if (Pattern.matches("[a-z]", Character.toString(letras[i]))) estado = "q10";
                    else if (Pattern.matches("[.]", Character.toString(letras[i]))) estado = "q12";
                    else estado = "error";
                    break;
                case "q10":
                    if (Pattern.matches("[a-z]", Character.toString(letras[i]))) estado = "q11";
                    else if (Pattern.matches("[.]", Character.toString(letras[i]))) estado = "q12";
                    else estado = "error";
                    break;
                case "q11":
                    if (Pattern.matches("[.]", Character.toString(letras[i]))) estado = "q12";
                    else estado = "error";
                    break;
                case "q12":
                    if (Pattern.matches("[a-z]", Character.toString(letras[i]))) estado = "q13";
                    else estado = "error";
                    break;
                case "q13":
                    if (Pattern.matches("[a-z]", Character.toString(letras[i]))) estado = "q14";
                    else estado = "error";
                    break;
                case "q14":
                case "error":
                    estado = "error";
                    break;
            }
        }

        if (estado.equals("q9") || estado.equals("q10") || estado.equals("q11") || estado.equals("q14")) {
            return "Correo valido";
        } else {
            return "Correo no valido";
        }
    }
}
