package newton.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import newton.FXCustomClasses.PreciseTimePicker;
import newton.Main;
import newton.interfaces.IReaction;
import newton.interfaces.ITrigger;
import newton.modules.Rule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class RuleAdder {

    private ArrayList<ITrigger> triggers = new ArrayList<>();
    private ArrayList<IReaction> reactions = new ArrayList<>();
    private LocalDateTime startLine = null;
    private LocalDateTime expirationDate = null;
    private TriggerAdder triggerAdder = new TriggerAdder();
    private ReactionAdder reactionAdder = new ReactionAdder();

    @FXML public void switchToMainWindow(ActionEvent event){
        SceneManager.changeScene(event, "mainWindow.fxml");
    }

    private static Stage stage;
    private static Parent addRulefxmlFile;




    private static PreciseTimePicker startLineField = new PreciseTimePicker("NOW", "start time :          ");
    private static PreciseTimePicker expirationDateField = new PreciseTimePicker("INFINITY", "expiration date : ");

    public static void makeScene(){
        VBox newStuff = new VBox();
        startLineField.reset();
        expirationDateField.reset();
        newStuff.getChildren().addAll(startLineField, expirationDateField, addRulefxmlFile);

        Scene scene = new Scene(newStuff);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Hamoksha");
        stage.show();
    }

    HashMap<String, ArrayList<String>> triggersVariablesLists;
    HashMap<String, ArrayList<String>> reactionsVariablesLists;
    public RuleAdder(){

        triggersVariablesLists = new HashMap<>();
        reactionsVariablesLists = new HashMap<>();

        triggersVariablesLists.put("TimeTrigger", new ArrayList<>());
        triggersVariablesLists.get("TimeTrigger").add("startTime");
        triggersVariablesLists.get("TimeTrigger").add("repeatingUnit");
        triggersVariablesLists.get("TimeTrigger").add("repeatingInterval");

        triggersVariablesLists.put("MachineStartTrigger", new ArrayList<>());

        triggersVariablesLists.put("MemoryConsumptionTrigger", new ArrayList<>());
        triggersVariablesLists.get("MemoryConsumptionTrigger").add("levelOfConsumption");


        triggersVariablesLists.put("CPUConsumptionTrigger", new ArrayList<>());
        triggersVariablesLists.get("CPUConsumptionTrigger").add("levelOfConsumption");

        triggersVariablesLists.put("DiskConsumptionTrigger", new ArrayList<>());
        triggersVariablesLists.get("DiskConsumptionTrigger").add("levelOfConsumption");

        triggersVariablesLists.put("BatteryConsumptionTrigger", new ArrayList<>());
        triggersVariablesLists.get("BatteryConsumptionTrigger").add("levelOfConsumption");


        reactionsVariablesLists.put("Notification", new ArrayList<>());
        reactionsVariablesLists.get("Notification").add("title");
        reactionsVariablesLists.get("Notification").add("message");

        reactionsVariablesLists.put("CommandExecutor", new ArrayList<>());
        reactionsVariablesLists.get("CommandExecutor").add("command");

        reactionsVariablesLists.put("FileOpener", new ArrayList<>());
        reactionsVariablesLists.get("FileOpener").add("filePath");

        triggerAdder.setTriggersVariablesLists(triggersVariablesLists);
        reactionAdder.setReactionsVariablesLists(reactionsVariablesLists);

    }

    @FXML private ComboBox triggerTypeBox;
    public void triggerTypeBoxClicked(){
        ArrayList<String> triggerTypesList = new ArrayList<>(triggersVariablesLists.keySet());
        triggerTypeBox.getItems().addAll(triggerTypesList);
    }

    @FXML private ComboBox reactionTypeBox;
    public void RactionTypeBoxClicked(){
        ArrayList<String> reactionTypesList = new ArrayList<>(reactionsVariablesLists.keySet());
        reactionTypeBox.getItems().addAll(reactionTypesList);
    }


    public void showTriggers(ActionEvent event){
        selectedTriggerType = (String) triggerTypeBox.getValue().toString();
        triggerAdder.setSelectedTriggerType(selectedTriggerType);

        VBox bigBox = new VBox(14);
        bigBox.getChildren().addAll(startLineField, expirationDateField, triggerAdder.createTriggerVBox(), reactionAdder.getvBoxForReactionTextFields(), addRulefxmlFile);
        Scene scene = new Scene(bigBox);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }


    public void showReactions(ActionEvent event){
        selectedReactionType = (String) reactionTypeBox.getValue().toString();
        reactionAdder.setSelectedReactionType(selectedReactionType);

        VBox bigBox = new VBox(14);
        bigBox.getChildren().addAll(startLineField, expirationDateField, triggerAdder.getvBoxForTriggerTextFields(), reactionAdder.createReactionVBox(), addRulefxmlFile);
        Scene scene = new Scene(bigBox);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }


    private String selectedTriggerType;
    String selectedReactionType = "";

    public void addTrigger(ActionEvent event){ //the most important function
        try{
            triggers.add(triggerAdder.getTrigger());
            SceneManager.showSuccess("Done", "A trigger has been added successfully");
        }
        catch (IllegalArgumentException e){
            SceneManager.showError("Invalid trigger information", "Please, enter a valid trigger");
        }
        catch (Exception e){
            SceneManager.showError("Error", "Missing information !");
        }
    }


    public void addReaction(ActionEvent event){
        //if the user has entere something and don't want to you know

        try{
            reactions.add(reactionAdder.getReaction());
            SceneManager.showSuccess("Done", "A reaction has been added successfully");
        } catch (IllegalArgumentException e) {
            SceneManager.showError("Invalid reaction information", e.getMessage());
        }
        catch (Exception e){
            SceneManager.showError("Error", "Missing information !");
        }
    }

    public void addRule(ActionEvent event) {

        startLine = startLineField.getSelectedDateTime();
        expirationDate = expirationDateField.getSelectedDateTime();

        boolean isexpirationDateGone = (expirationDate.compareTo(LocalDateTime.now()) < 0);
        if(startLine == null || expirationDate == null || isexpirationDateGone){
            SceneManager.showError("Missing Information", "Please, enter a trigger(s), a reaction(s), start time and expiration date");
            return;
        }

        if(!triggerAdder.areAllFieldsEmpty()) {
            addTrigger(event);
        }

        if(!reactionAdder.areAllFieldsEmpty()) {
            addReaction(event);
        }

        if(triggers.isEmpty() || reactions.isEmpty()){
            SceneManager.showError("Missing Information", "Please, enter a trigger(s) and a reaction(s)");
            return;
        }

        //create the
        // rule and add it to the memory and the database
        Rule rule = new Rule(startLine, expirationDate, triggers, reactions);
        Main.addRule(rule);
        //change the scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainWindow.fxml"));
        SceneManager.showSuccess("Done", "A rule has been added successfully");

        try{
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            stage.setScene(scene);
        }
        catch (IOException e){System.err.println("Failed to load main window");}

    }

    public static void setAddRulefxmlFile(Parent p){
        addRulefxmlFile = p;
    }
    public static void setStage(Stage s){
        stage = s;
    }
}


