package Clutch.Controllers;

import javafx.event.ActionEvent;

public class MainController {
    public void SwitchToAddRule(ActionEvent event){
        SceneManager.switchToRuleAdder(event);
    }

    public void switchToDeleteRule(ActionEvent event){
        SceneManager.changeScene(event, "DeleteRule.fxml");
    }
}
