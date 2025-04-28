package Clutch.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import Clutch.Main;

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
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).replace(',', '\n');
            String s2 = s.replace("\"", "");
            list.set(i, s2);
        }
        rulesList.getItems().addAll(list);
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
                SceneManager.showError("deletion error", e.getMessage());
                return;
            }
        }
        SceneManager.showSuccess("Success", "Rules deleted successfully");
    }

    private int extractID(String selectedItem) throws Exception {
        //look for the word id
        for(int i=0; i<selectedItem.length() - 1; i++){
            if(selectedItem.charAt(i)=='i' && selectedItem.charAt(i+1)=='d'){ //found!
                //now let's talk about the id itself as a number reperesented as a string
                String number = new String("");
                for(int j = i + 3; j<selectedItem.length(); j++){ //let's  continue adding the numbers until we find a \n or space
                    if(selectedItem.charAt(j)=='\n' || selectedItem.charAt(j)==' '){
                        return Integer.parseInt(number); //in that case return that number as an integer
                    }
                    number += selectedItem.charAt(j);
                }
            }
        }
        throw new Exception("ID extraction failed!");
    }

    public void backToMain(ActionEvent actionEvent) {
        SceneManager.changeScene(actionEvent, "mainWindow.fxml");
    }
//[{"id":0,"start_life":[2025,3,1,6,27,33,243956800],"expirationDate":[999999999,12,31,23,59,59,999999999],"triggers":[{"type":"machineStart"}],"reactions":[{"type":"notification","title":"title","message":"message"}],"idCounter":2},{"id":1,"start_life":[2025,3,1,6,49,37,434391700],"expirationDate":[999999999,12,31,23,59,59,999999999],"triggers":[{"type":"machineStart"}],"reactions":[{"type":"commandExecutor","command":"notepad"}],"idCounter":2}]

}


/*
*
jpackage --input target/ \
         --name MyApp \
         --main-jar myapp.jar \
         --main-class com.example.MainApp \
         --type exe \
         --icon src/main/resources/icon.ico \
         --dest output/





* */
