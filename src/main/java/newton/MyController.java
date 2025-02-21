package newton;

import javafx.event.ActionEvent;

public class MyController {
    public void SwitchToAddRule(ActionEvent event){
        Scenemanager.switchToRuleAdder(event, "AddRule.fxml");
    }

}
