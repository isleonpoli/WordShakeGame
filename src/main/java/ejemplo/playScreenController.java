package ejemplo;


import model.wordFound;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import conexion.dbMethods;



public class playScreenController implements Initializable {

    @FXML
    private Button newGameButton;

    @FXML
    private Button backButton;

    @FXML
    private Label timerLabel;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnEnter;

    @FXML
    private TableColumn<wordFound, Integer> colPoints;

    @FXML
    private TableColumn<wordFound, String> colWord;

    @FXML
    private TableView<wordFound> tabWfound;

    @FXML
    private TextField txtWord;

    @FXML
    private Label confirmLabel;

    @FXML
    private GridPane letters;


    @FXML
    private ImageView playScreen;


    private int secondsRemaining = 180;
    private Timeline timeline;
    private ObservableList<wordFound> wordFounds = FXCollections.observableArrayList();
    private StringBuilder palabraFormada = new StringBuilder(); 
    // Variable para guardar las letras seleccionadas

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

    
    // Cargar imagen
    File profileFile = new File("Pantallas/play.png");
    Image profileImage = new Image(profileFile.toURI().toString());
    playScreen.setImage(profileImage);

    // Inicialización de tabla
    colWord.setCellValueFactory(
        data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPalabra()));
    colPoints.setCellValueFactory(
        data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getPuntaje()).asObject());
    tabWfound.setItems(wordFounds);

    btnEnter.setVisible(true);
    btnCancel.setVisible(true);
    txtWord.setVisible(true);

    // Generar letras
    generarLetras();


  
    // Iniciar temporizador
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        secondsRemaining--;
        updateLabel();
        if (secondsRemaining <= 0) {
            timeline.stop();
            timerLabel.setText("¡Tiempo terminado!");
            btnCancel.setVisible(false);
            btnEnter.setVisible(false);
            txtWord.setVisible(false);
            
            int puntuacionTotal = 0;

            for (wordFound wf : wordFounds) {
                puntuacionTotal += wf.getPuntaje();
            }

            confirmLabel.setText("Your score is: " + puntuacionTotal);
            confirmLabel.setVisible(true);  

            String username = session.getUsername();
            dbMethods.actualizarPuntajeUsuario(username, puntuacionTotal);
        }
    }));

    timeline.setCycleCount(secondsRemaining);
    timeline.play();
}

    private void updateLabel() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void generarLetras() {
    letters.getChildren().clear(); // Limpia la cuadrícula

    //letras sin vocales
    String letras = "BCDFGHJKLMNPQRSTVWXYZ";
    String vocales = "AEIOU";

    List<Character> letrasGeneradas = new ArrayList<>();

    // 1. Asegurarse de tener al menos 2 de cada vocal
    for (char vocal : vocales.toCharArray()) {
        letrasGeneradas.add(vocal);
        letrasGeneradas.add(vocal);
    }

    // 2. Rellenar hasta 25 letras con letras aleatorias (pueden incluir más vocales o consonantes)
    Random rand = new Random();
    while (letrasGeneradas.size() < 25) {
        letrasGeneradas.add(letras.charAt(rand.nextInt(letras.length())));
    }

    // 3. Mezclar las letras
    Collections.shuffle(letrasGeneradas);

    // 4. Colocar letras en la cuadrícula como ToggleButtons
    int index = 0;
    for (int fila = 0; fila < 5; fila++) {
        for (int columna = 0; columna < 5; columna++) {
            char letra = letrasGeneradas.get(index++);

            ToggleButton toggleButton = new ToggleButton(String.valueOf(letra));
            toggleButton.setPrefWidth(105);
            toggleButton.setPrefHeight(105);

            toggleButton.setOnAction(e -> {
                if (toggleButton.isSelected()) {
                    palabraFormada.append(toggleButton.getText());
                    txtWord.setText(palabraFormada.toString());
                } else {
                    int idx = palabraFormada.indexOf(toggleButton.getText());
                    if (idx != -1) {
                        palabraFormada.deleteCharAt(idx);
                        txtWord.setText(palabraFormada.toString());
                    }
                }
            });

            letters.add(toggleButton, columna, fila);
        }
    }
}

    @FXML
    private void onBtnCancelClick(ActionEvent event) {
        for (Node node : letters.getChildren()) {
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false); // Desmarcar el botón
            }
        }
        // Limpiar el TextField después de cancelar
        txtWord.clear();
        palabraFormada.setLength(0); // Limpiar la palabra formada
    }


    @FXML
    void newGameButtonOnAction(ActionEvent event) {
        txtWord.setVisible(true);
        btnCancel.setVisible(true);
        btnEnter.setVisible(true);
        //Esconder label
        confirmLabel.setVisible(false);
        // Reiniciar tiempo
        if (timeline != null) {
            timeline.stop();
        }
        
        secondsRemaining = 180; // 3 minutos otra vez
        updateLabel();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsRemaining--;
            updateLabel();
            if (secondsRemaining <= 0) {
                timeline.stop();
                timerLabel.setText("¡Tiempo terminado!");
                btnCancel.setVisible(false);
                btnEnter.setVisible(false);
                confirmLabel.setVisible(false);

                int puntuacionTotal = 0;

            for (wordFound wf : wordFounds) {
                puntuacionTotal += wf.getPuntaje();
            }

            confirmLabel.setText("Your score is: " + puntuacionTotal);
            confirmLabel.setVisible(true);  

            String username = session.getUsername();
            dbMethods.actualizarPuntajeUsuario(username, puntuacionTotal);
            }
        }));
        timeline.setCycleCount(secondsRemaining);
        timeline.play();

        // Regenerar letras
        generarLetras();

        // Limpiar palabra actual y tabla
        txtWord.clear();
        palabraFormada.setLength(0);
        wordFounds.clear(); // Si también deseas limpiar las palabras encontradas
    }


    @FXML
    void backButtonOnAction(ActionEvent event) {
            wordFounds.clear();
            if (timeline != null) {
            timeline.stop(); // Detener el temporizador
            }
            sceneManager.changeScene("profileScreen.fxml", true);
    }

    @FXML
    void onBtnEnterClick(ActionEvent event) {
        String palabra = txtWord.getText().trim().toLowerCase();
        if (palabra.isEmpty())
            return;
        // Verifica si ya está en la tabla
        for (wordFound p : wordFounds) {
            if (p.getPalabra().equals(palabra)) {   
                confirmLabel.setText("word already entered");
                confirmLabel.setVisible(true);
                confirmLabel.setLayoutX(367);
                txtWord.clear();
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> confirmLabel.setVisible(false));
                pause.play();
                for (Node node : letters.getChildren()) {
                    if (node instanceof ToggleButton) {
                        ((ToggleButton) node).setSelected(false); // Desmarcar el botón
                    }
                }
                // Limpiar el TextField después de enviar la palabra
                txtWord.clear();
                palabraFormada.setLength(0); // Limpiar la palabra formada
                return;
                
            }
        }

        // Consultar puntaje desde la base de datos
        Integer puntos = dbMethods.obtenerPuntajePalabra(palabra);
        if (puntos != null) {
            confirmLabel.setText("Correct :)");
            confirmLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> confirmLabel.setVisible(false));
            pause.play();
            wordFounds.add(new wordFound(palabra, puntos));
            txtWord.clear();
        } else {
            confirmLabel.setText("Invalid word :(");
            confirmLabel.setVisible(true);
            txtWord.clear();
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> confirmLabel.setVisible(false));
            pause.play();
        }

        for (Node node : letters.getChildren()) {
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false); // Desmarcar el botón
            }
        }
        // Limpiar el TextField después de enviar la palabra
        txtWord.clear();
        palabraFormada.setLength(0); // Limpiar la palabra formada
    }

}
