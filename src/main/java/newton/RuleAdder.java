package newton;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import newton.Builders.RactionBuilder;
import newton.Builders.TriggerBuilder;
import newton.FXCustomClasses.PreciseTimePicker;
import newton.interfaces.IReaction;
import newton.interfaces.ITrigger;
import newton.modules.Rule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class RuleAdder {


    private ArrayList<ITrigger> triggers = new ArrayList<>();
    private ArrayList<IReaction> reactions = new ArrayList<>();
    private LocalDateTime startLine = null;
    private LocalDateTime expirationDate = null;


    @FXML public void switchToMainWindow(ActionEvent event){
        SceneSwitcher.changeScene(event, "mainWindow.fxml");
    }



    private static Stage stage;
    public static void setStage(Stage s){
        stage = s;
    }

    private static Parent addRulefxmlFile;
    public static void setAddRulefxmlFile(Parent p){
        addRulefxmlFile = p;
    }

    public static void makeScene(){

        VBox newStuff = new VBox();
        newStuff.getChildren().addAll(startLineField, expirationDateField, addRulefxmlFile);

        Scene scene = new Scene(newStuff);
        scene.getStylesheets().add(RuleAdder.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Hamoksha");
        stage.show();
    }

    private static PreciseTimePicker startLineField = new PreciseTimePicker("NOW");
    private static PreciseTimePicker expirationDateField = new PreciseTimePicker("INFINITY");


    public void setPreciseTimePickers(PreciseTimePicker p1, PreciseTimePicker p2){
        startLineField = p1;
        expirationDateField = p2;
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


        reactionsVariablesLists.put("Notification", new ArrayList<>());
        reactionsVariablesLists.get("Notification").add("title");
        reactionsVariablesLists.get("Notification").add("message");

        reactionsVariablesLists.put("CommandExecutor", new ArrayList<>());
        reactionsVariablesLists.get("CommandExecutor").add("command");

        reactionsVariablesLists.put("FileOpener", new ArrayList<>());
        reactionsVariablesLists.get("FileOpener").add("filePath");


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


    public void handelTriggerComboBox(ActionEvent event){
        vBoxForTriggerTextFields.getChildren().clear();

        selectedTriggerType = (String) triggerTypeBox.getValue().toString();

        vBoxForTriggerTextFields.setPadding(new Insets(0,0,0,10));


        if(selectedTriggerType.equals("TimeTrigger")){
            //hat7ebeni walla Ato5 rohi betabanga

            Text t1 = new Text("Starting Time");
            t1.setFont(textFont);
            vBoxForTriggerTextFields.getChildren().add(t1);


            vBoxForTriggerTextFields.getChildren().add(startTriggerField);

            //create the combo box for the repeating unit

            Text t2 = new Text("Repeating Unit");
            t2.setFont(textFont);
            vBoxForTriggerTextFields.getChildren().add(t2);

            repeatingUnitBox.setItems(FXCollections.observableArrayList("SECONDS", "MINUTES", "HOURS", "DAYS", "WEEKS", "MONTHS", "YEARS"));
            vBoxForTriggerTextFields.getChildren().add(repeatingUnitBox);


            //whoever is reading this piece of code, I love you
            //Free Palestine

            //create the textfield  for the repeating interval
            Text t3 = new Text("Repeating Interval");
            t3.setFont(textFont);
            vBoxForTriggerTextFields.getChildren().add(t3);
            repeatingIntervalField.setPrefWidth(60.0);
            vBoxForTriggerTextFields.getChildren().add(repeatingIntervalField);

        }
        else {
            //creat a number of text fields that equals the number of variables inside the trigger
            //and beside those we need text that represents what this text field is all about
            for(String triggerVariableName : triggersVariablesLists.get(selectedTriggerType)){ //for every variable you have, make a text and a text field
                //creat text
                Text text = new Text(triggerVariableName); //make a label so that the user knows what this is
                text.setFont(textFont);
                triggerTexts.add(text);
                vBoxForTriggerTextFields.getChildren().add(text);

                //creat text field
                TextField field = new TextField(triggerVariableName);
                field.setFont(inputFont);
                triggerFields.add(field);
                vBoxForTriggerTextFields.getChildren().add(field);
            }
        }

        VBox bigBox = new VBox(14);
        bigBox.getChildren().addAll(startLineField, expirationDateField,vBoxForTriggerTextFields, vBoxForReactionTextFields, addRulefxmlFile);
        Scene scene = new Scene(bigBox);
        scene.getStylesheets().add(RuleAdder.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }


    public void handelReactionComboBox(ActionEvent event){
        vBoxForReactionTextFields.getChildren().clear();
        vBoxForReactionTextFields.setPadding(new Insets(0,0,0,10));

        selectedReactionType = reactionTypeBox.getValue().toString();

        for(String reactionVariableName : reactionsVariablesLists.get(selectedReactionType)){
            Text text = new Text(reactionVariableName);
            text.setFont(textFont);
            reactionTexts.add(text);
            vBoxForReactionTextFields.getChildren().add(text);

            TextField field = new TextField(reactionVariableName);
            field.setPromptText(reactionVariableName);
            field.setFont(inputFont);
            reactionFields.add(field);
            vBoxForReactionTextFields.getChildren().add(field);
        }
        VBox bigBox = new VBox(14);
        bigBox.getChildren().addAll(startLineField, expirationDateField,vBoxForTriggerTextFields, vBoxForReactionTextFields, addRulefxmlFile);
        Scene scene = new Scene(bigBox);
        scene.getStylesheets().add(RuleAdder.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }


    //TODO : if you really don't like the object creating here, you can make an array of objects or array of controls to handel
    //TODO : the different cases of creating input takers (I know it's a little missy here, but 15 continual hour of making a gui is already brain consuming)
    private ArrayList<Text> triggerTexts = new ArrayList<>();
    private ArrayList<TextField> triggerFields = new ArrayList<>();

    private String selectedTriggerType;

    //for TimeTrigger specifically
    private PreciseTimePicker startTriggerField = new PreciseTimePicker("NOW");
    private ComboBox<String> repeatingUnitBox = new ComboBox<>();
    private TextField repeatingIntervalField = new TextField();


    VBox vBoxForTriggerTextFields = new VBox(14);
    Font textFont = Font.font("Arial", FontWeight.BOLD,16);
    Font inputFont = Font.font("Arial", FontWeight.NORMAL,13);


    String selectedReactionType = "";
    private ArrayList<Text> reactionTexts = new ArrayList<>();
    private ArrayList<TextField> reactionFields = new ArrayList<>();
    VBox vBoxForReactionTextFields = new VBox(14);


    public void addTrigger(ActionEvent event){ //the most important function

        HashMap<String, String> variables = new HashMap<>();
        try{
            if(selectedTriggerType.equals("TimeTrigger")){
                //we are going to check over the fields we have
                variables.put("startTime", startTriggerField.getSelectedDateTime().toString());
                variables.put("repeatingUnit", repeatingUnitBox.getValue());
                variables.put("repeatingInterval", repeatingIntervalField.getText());
            }
            else{
                //checkout the ArrayList of TextFields to see what is written
                Iterator<TextField> fieldIterator = triggerFields.iterator();
                Iterator<String> variableNameIterator = triggersVariablesLists.get(selectedTriggerType).iterator();

                while (fieldIterator.hasNext() && variableNameIterator.hasNext())
                    variables.put(variableNameIterator.next(), fieldIterator.next().getText());

            }
            triggers.add(TriggerBuilder.build(selectedTriggerType, variables));
            showSuccess("Done", "A trigger has been added successfully");
        }
        catch (IllegalArgumentException e){
            showError("Invalid trigger information", "Please, enter a valid trigger");
        }
        catch (Exception e){
            showError("Error", "Missing information !");
        }
        //clear all the text fields
        clearTriggerFields();
    }


    public void addReaction(ActionEvent event){
        HashMap<String, String> variables = new HashMap<>();

        try{
            Iterator<TextField> fieldIterator = reactionFields.iterator();
            Iterator<String> variableNameIterator = reactionsVariablesLists.get(selectedReactionType).iterator();

            while(fieldIterator.hasNext() && variableNameIterator.hasNext())
                variables.put(variableNameIterator.next(), fieldIterator.next().getText());

            reactions.add(RactionBuilder.build(selectedReactionType, variables));
            showSuccess("Done", "A reaction has been added successfully");
        } catch (IllegalArgumentException e) {
            showError("Invalid reaction information", e.getMessage());
        }
        catch (Exception e){
            showError("Error", "Missing information !");
        }
        clearReactionFields();
    }

    public void addRule(ActionEvent event) {
        startLine = startLineField.getSelectedDateTime();
        expirationDate = expirationDateField.getSelectedDateTime();

        boolean isexpirationDateGone = (expirationDate.compareTo(LocalDateTime.now()) < 0);
        if(startLine == null || expirationDate == null || triggers.isEmpty() || reactions.isEmpty() || isexpirationDateGone){
            showError("Missing Information", "Please, enter a trigger(s), a reaction(s), start time and expiration date");
            return;
        }

        //create the rule and add it to the memory and the database
        Rule rule = new Rule(startLine, expirationDate, triggers, reactions);
        Main.addRule(rule);
        //change the scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        showSuccess("Done", "A rule has been added successfully");
        try{
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
        }
        catch (IOException e){System.err.println("Failed to load main window");}

    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title); // Set the title of the dialog
        alert.setHeaderText(null); // Optional: Remove the header text
        alert.setContentText(content); // Set the content/message

        // Show the dialog
        alert.showAndWait();
    }

    private static void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Remove header text for a cleaner look
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void clearTriggerFields() {
        for(TextField field : triggerFields)
            field.clear();

        startTriggerField.reset();
        repeatingIntervalField.clear();
    }

    private void clearReactionFields() {
        for (TextField field : reactionFields)
            field.clear();
    }



}