package newton;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Objects;


public class SceneManager {

    public static void switchToRuleAdder(ActionEvent event) {

        try {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            RuleAdder.setStage(window);

            Parent root = FXMLLoader.load(SceneManager.class.getResource("AddRule.fxml"));
            RuleAdder.setAddRulefxmlFile(root);
            RuleAdder.makeScene();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlFile)));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(SceneManager.class.getResource("style.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title); // Set the title of the dialog
        alert.setHeaderText(null); // Optional: Remove the header text
        alert.setContentText(content); // Set the content/message

        // Show the dialog
        alert.showAndWait();
    }

    public static void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Remove header text for a cleaner look
        alert.setContentText(content);
        alert.showAndWait();
    }


}
