package newton;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class Scenemanager {

    public static void switchToRuleAdder(ActionEvent event, String fxmlFile) {

        try {
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            RuleAdder.setStage(window);

            Parent root = FXMLLoader.load(Scenemanager.class.getResource(fxmlFile));
            RuleAdder.setAddRulefxmlFile(root);
            RuleAdder.makeScene();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fuck javafx");
        }
    }


}
