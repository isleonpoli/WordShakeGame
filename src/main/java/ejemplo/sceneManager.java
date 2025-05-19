package ejemplo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class sceneManager {
    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    // Nuevo método con opción para pantalla completa
    public static void changeScene(String fxml, boolean fullscreen) {
        try {
            FXMLLoader loader = new FXMLLoader(sceneManager.class.getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(fullscreen); // ← Aquí activas o no pantalla completa
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Si quieres mantener el método original para escenas normales
    public static void changeScene(String fxml) {
        changeScene(fxml, false); // Por defecto: no pantalla completa
    }
}