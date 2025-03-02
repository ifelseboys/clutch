package newton.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import newton.Main;

import java.util.List;

public class RuleDeleter {

    @FXML private ListView<String> rulesList;

    //TODO : a possible error here is that when scene get switched here : the rules aren't updated, consider that if it doesn't work
    public RuleDeleter(){
    }

    @FXML
    private void initialize() {
        rulesList.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        List<String> list = Main.getStringRules();
        rulesList.getItems().addAll(list); // Populate data here
    }

    public void deleteRule() {
        ObservableList<String> selectedItems = rulesList.getSelectionModel().getSelectedItems();

        for(int i=0; i<selectedItems.size(); i++){
            String selectedItem = selectedItems.get(i);
            try{
                int id = extractID(selectedItem);
                Main.deleteRule(id);
                rulesList.getItems().remove(i);
                i--;
            }
            catch(Exception e){
                SceneManager.showError("deletion error", "something went wrong with deletion");
                return;
            }
        }
        SceneManager.showSuccess("Success", "Rules deleted successfully");
    }

    private int extractID(String selectedItem) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(selectedItem);
        return rootNode.get("id").asInt();
    }

    public void backToMain(ActionEvent actionEvent) {
        SceneManager.changeScene(actionEvent, "mainWindow.fxml");
    }
//[{"id":0,"start_life":[2025,3,1,6,27,33,243956800],"expirationDate":[999999999,12,31,23,59,59,999999999],"triggers":[{"type":"machineStart"}],"reactions":[{"type":"notification","title":"title","message":"message"}],"idCounter":2},{"id":1,"start_life":[2025,3,1,6,49,37,434391700],"expirationDate":[999999999,12,31,23,59,59,999999999],"triggers":[{"type":"machineStart"}],"reactions":[{"type":"commandExecutor","command":"notepad"}],"idCounter":2}]

}
