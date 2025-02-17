package newton.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import newton.Builders.RactionBuilder;
import newton.Builders.TriggerBuilder;
import newton.Exceptions.InvalidTriggerInformation;
import newton.FXCustomClasses.PreciseTimePicker;
import newton.interfaces.IDatabase;
import newton.interfaces.IReaction;
import newton.interfaces.ITrigger;
import newton.modules.Rule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;



public class RuleAdder {

    private CopyOnWriteArrayList<Rule> rulesList;
    private IDatabase database;

    private ArrayList<ITrigger> triggers = new ArrayList<>();
    private ArrayList<IReaction> reactions = new ArrayList<>();
    private LocalDateTime startLine = null;
    private LocalDateTime expirationDate = null;

    @FXML public void switchToMainWindow(ActionEvent event){
        Scenemanager.changeScene(event, "MainWindow.fxml");
    }


    //TODO: Must be called first when you use the class
    HashMap<String, ArrayList<String>> triggersMap; //maps the Trigger -> list of variables
    HashMap<String, ArrayList<String>> reactionsMap;
    public void initialize(HashMap<String, ArrayList<String>> x, HashMap<String, ArrayList<String>> y, CopyOnWriteArrayList<Rule> rules, IDatabase database) {
        triggersMap = x;
        reactionsMap = y;
        this.rulesList = rules;
        this.database = database;
    }


    private PreciseTimePicker startLineField;
    private PreciseTimePicker expirationDateField;

    @FXML private ComboBox triggerTypeBox;
    public void handelTriggerComboBox(ActionEvent event){
        ArrayList<String> triggerTypesList = new ArrayList<>(triggersMap.keySet());
        triggerTypeBox.getItems().setAll(triggerTypesList); //when the user pushes, the types pop up

        triggerTypeBox.setOnAction(event1-> {showTriggerInputs(event);}); //when a user chooses something, create a list of text boxes that represent trigger's internal variables
    }

    public void handelRactionComboBox(ActionEvent event){
        ArrayList<String> RactioinTypesList = new ArrayList<>(reactionsMap.keySet());
        ReactionTypeBox.getItems().setAll(RactioinTypesList);

        ReactionTypeBox.setOnAction(event1-> {showRactionInputs(event);});
    }


    //TODO : if you really don't like the object creating here, you can make an array of objects or array of controls to handel
    //TODO : the different cases of creating input takers (I know it's a little missy here, but 15 continual hour of making a gui is already brain consuming)
    private ArrayList<Text> triggerTexts = new ArrayList<>();
    private ArrayList<TextField> triggerFields = new ArrayList<>();

    private String selectedTriggerType;

    //for TimeTrigger specifically
    private PreciseTimePicker startTriggerField;
    private ComboBox<String> repeatingUnitBox = new ComboBox<>();

    private TextField repeatingIntervalField = new TextField();


    public void showTriggerInputs(Event e){
        selectedTriggerType = (String) triggerTypeBox.getValue().toString();

        double XstartingPosition = 8;
        double YstartingPosition = 80;
        double width = 60;
        double verticalSpace = 70;
        double horizontalSpace = 25;


        if(selectedTriggerType.equals("TimeTrigger")){
            //hat7ebeni walla Ato5 rohi betabanga


            //create the combo box for the repeating unit
            repeatingUnitBox.setLayoutX(XstartingPosition);
            repeatingUnitBox.setLayoutY(YstartingPosition);
            YstartingPosition += horizontalSpace;
            repeatingUnitBox.setPrefWidth(width);
            repeatingUnitBox.setItems(FXCollections.observableArrayList("SECOND", "MINUTE", "HOUR", "DAY", "WEEK", "MONTH", "YEAR"));

            //whoever is reading this piece of code, I love you
            //Free Palestine

            //create the textfield  for the repeating interval
            repeatingUnitBox.setLayoutX(XstartingPosition);
            repeatingUnitBox.setLayoutY(YstartingPosition);
            repeatingIntervalField.setPrefWidth(width);

        }
        else {
            //creat a number of text fields that equals the number of variables inside the trigger
            //and beside those we need text that represents what this text field is all about
            int i = 0;
            for(String triggerVariableName : triggersMap.get(selectedTriggerType)){ //for every variable you have, make a text and a text field
                //creat text
                Text text = new Text(triggerVariableName); //make a label so that the user knows what this is
                text.setLayoutX(XstartingPosition);
                text.setLayoutY(YstartingPosition + i * verticalSpace);
                triggerTexts.add(text);

                //creat text field
                TextField field = new TextField(triggerVariableName);
                field.setLayoutX(XstartingPosition + width + horizontalSpace);
                field.setLayoutY(YstartingPosition + i * verticalSpace);
                triggerFields.add(field);
                i++;
            }
        }
    }
    @FXML ComboBox ReactionTypeBox;
    String selectedReactionType = "";


    private ArrayList<Text> reactionTexts = new ArrayList<>();
    private ArrayList<TextField> reactionFields = new ArrayList<>();
    public void showRactionInputs(Event e){
        selectedReactionType = (String) ReactionTypeBox.getValue().toString();

        double XstartingPosition = 0;
        double YstartingPosition = 84;
        double width = 60;
        double verticalSpace = 70;
        double horizontalSpace = 25;

        int i = 0;
        for(String reactionVariableName : reactionsMap.get(selectedReactionType)){
            Text text = new Text(reactionVariableName);
            text.setLayoutX(XstartingPosition);
            text.setLayoutY(YstartingPosition + i * verticalSpace);
            reactionTexts.add(text);

            TextField field = new TextField(reactionVariableName);
            field.setLayoutX(XstartingPosition + width + horizontalSpace);
            field.setLayoutY(YstartingPosition + i * verticalSpace);
            reactionFields.add(field);
            i++;
        }

    }


    public void addTrigger(ActionEvent event){ //the most important function


        HashMap<String, String> variables = new HashMap<>();
        try{
            if(selectedTriggerType.equals("TimeTrigger")){
                //we are going to check over the fields we have
                variables.put("startTime", startTriggerField.getSelectedDateTime().toString());
                variables.put("repeatingUnit", repeatingUnitBox.getValue().toString());
                variables.put("repeatingInterval", repeatingIntervalField.getText());
            }
            else{
                //checkout the ArrayList of TextFields to see what is written
                Iterator<TextField> fieldIterator = triggerFields.iterator();
                Iterator<String> variableNameIterator = triggersMap.get(selectedTriggerType).iterator();

                while (fieldIterator.hasNext() && variableNameIterator.hasNext())
                    variables.put(variableNameIterator.next(), fieldIterator.next().getText());

            }
            triggers.add(TriggerBuilder.build(selectedTriggerType, variables));
        }
        catch (InvalidTriggerInformation e){
            showError("Invalid trigger information !", "Please, enter a valid trigger");
        }
        //clear all the text fields
        clearTriggerFields();
    }



    public void addReaction(ActionEvent event){
        HashMap<String, String> variables = new HashMap<>();

        try{
            Iterator<TextField> fieldIterator = reactionFields.iterator();
            Iterator<String> variableNameIterator = reactionsMap.get(selectedReactionType).iterator();

            while(fieldIterator.hasNext() && variableNameIterator.hasNext())
                variables.put(variableNameIterator.next(), fieldIterator.next().getText());

            reactions.add(RactionBuilder.build(selectedReactionType, variables));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clearRactionFields();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title); // Set the title of the dialog
        alert.setHeaderText(null); // Optional: Remove the header text
        alert.setContentText(content); // Set the content/message

        // Show the dialog
        alert.showAndWait();
    }

    private void clearTriggerFields() {
        for(TextField field : triggerFields)
            field.clear();

        startTriggerField.reset();
        repeatingIntervalField.clear();
    }

    private void clearRactionFields() {
        for (TextField field : reactionFields)
            field.clear();
    }



    public void addRule(ActionEvent event){
        startLine = startLineField.getSelectedDateTime();
        expirationDate = expirationDateField.getSelectedDateTime();

        boolean isexpirationDateGone = expirationDate.compareTo(LocalDateTime.now()) < 0;
        if(startLine == null || expirationDate == null || triggers.isEmpty() || reactions.isEmpty() || isexpirationDateGone){
            showError("Missing Information", "Please, enter a trigger(s), a reaction(s), start time and expiration date");
        }
        else{
            //create the rule and add it to the memory and the database
            Rule rule = new Rule(startLine, expirationDate, triggers, reactions);
            rulesList.add(rule);
            database.updateRules(rulesList);
        }

        Scenemanager.changeScene(event, "main_window.fxml");
    }

}