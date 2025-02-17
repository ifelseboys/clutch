/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package newton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception { //start the GUI

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main_window.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            primaryStage.setTitle("Newton GUI");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(NullPointerException e){
            System.out.println("Could not find main window or style.css");
        }
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

}