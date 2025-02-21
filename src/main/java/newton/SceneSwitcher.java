package newton;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class SceneSwitcher {

    public static void changeScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(fxmlFile));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(SceneSwitcher.class.getResource("style.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
