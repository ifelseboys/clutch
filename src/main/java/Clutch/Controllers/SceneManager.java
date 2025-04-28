package Clutch.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Clutch.Main;

import java.util.Objects;


public class SceneManager {

    public static void switchToRuleAdder(ActionEvent event) {
        try {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            RuleAdder.setStage(window);

            Parent root = FXMLLoader.load(Main.class.getResource("AddRule.fxml"));
            RuleAdder.setAddRulefxmlFile(root);
            RuleAdder.makeScene();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //display an fxml file using it's name, supposing it exists in the resources directory
    public static void changeScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlFile)));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //what did you think this does ?
    public static void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title); // Set the title of the dialog
        alert.setHeaderText(null); // Optional: Remove the header text
        alert.setContentText(content); // Set the content/message

        // Show the dialog
        alert.showAndWait();
    }

    //you would be surprised if this doesn't just show a message of success with a title and a message, don't you
    public static void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Remove header text for a cleaner look
        alert.setContentText(content);
        alert.showAndWait();
    }


}
