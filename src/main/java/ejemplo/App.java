package ejemplo;




import javafx.application.Application;

import javafx.stage.Stage;


public class App extends Application {


    @Override
    public void start(Stage primaryStage) {
        sceneManager.setStage(primaryStage);
        sceneManager.changeScene("homeScreen.fxml");

        primaryStage.setTitle("WordShake Game");
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch();          
    }

}