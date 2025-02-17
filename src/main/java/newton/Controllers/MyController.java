package newton.Controllers;

import javafx.event.ActionEvent;

public class MyController {
    public void SwitchToAddRule(ActionEvent event){
        Scenemanager.changeScene(event, "AddRule.fxml");
    }
}
