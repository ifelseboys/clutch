package Clutch.Controllers;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import Clutch.Builders.TriggerBuilder;
import Clutch.FXCustomClasses.PreciseTimePicker;
import Clutch.interfaces.ITrigger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class TriggerAdder {


    private String selectedTriggerType;
    public void setSelectedTriggerType(String selectedTriggerType) {this.selectedTriggerType = selectedTriggerType;}
    Font textFont = Font.font("Arial", FontWeight.BOLD,16);
    Font inputFont = Font.font("Arial", FontWeight.NORMAL,13);


    private ArrayList<Text> triggerTexts = new ArrayList<>();
    private ArrayList<TextField> triggerFields = new ArrayList<>();
    VBox vBoxForTriggerTextFields = new VBox(14);


    private HashMap<String, ArrayList<String>> triggersVariablesLists;
    public void setTriggersVariablesLists(HashMap<String, ArrayList<String>> triggersVariablesLists) {this.triggersVariablesLists = triggersVariablesLists;}




    //for TimeTrigger specifically
    private PreciseTimePicker startTriggerField = new PreciseTimePicker("NOW", "start time :");
    private ComboBox<String> repeatingUnitBox = new ComboBox<>();
    private TextField repeatingIntervalField = new TextField();

    //creates the box containing all the information we need to take from the user
    public VBox createTriggerVBox(){
        clearTriggerFields();
        vBoxForTriggerTextFields.getChildren().clear();
        vBoxForTriggerTextFields.setPadding(new Insets(0,0,0,10));

        if(selectedTriggerType.equals("RepeatingTimeTrigger")){
            return createRepeatingTimeTriggerVBox();
        }
        else if (selectedTriggerType.equals("NonRepeatingTimeTrigger")){
            return createNonRepeatingTimeTriggerVBox();
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
        return vBoxForTriggerTextFields;
    }



    private VBox createRepeatingTimeTriggerVBox(){
        vBoxForTriggerTextFields.getChildren().clear();
        vBoxForTriggerTextFields.setPadding(new Insets(0,0,0,10));

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

        return vBoxForTriggerTextFields;
    }

    private PreciseTimePicker fireTime = new PreciseTimePicker("NOW", "start time :");
    private VBox createNonRepeatingTimeTriggerVBox(){
        vBoxForTriggerTextFields.getChildren().clear();
        vBoxForTriggerTextFields.setPadding(new Insets(0,0,0,10));
        vBoxForTriggerTextFields.getChildren().add(fireTime);
        return vBoxForTriggerTextFields;
    }


    //this creates the trigger based on the information provided by the user and the type of trigger he chose
    public ITrigger getTrigger(){

        HashMap<String, String> variables = new HashMap<>();

        if(selectedTriggerType.equals("RepeatingTimeTrigger")){
            //we are going to check over the fields we have
            variables.put("startTime", startTriggerField.getSelectedDateTime().toString());
            variables.put("repeatingUnit", repeatingUnitBox.getValue());
            variables.put("repeatingInterval", repeatingIntervalField.getText());
        }
        else if(selectedTriggerType.equals("NonRepeatingTimeTrigger")){
            variables.put("fireTime", fireTime.getSelectedDateTime().toString());
        }
        else{
            //checkout the ArrayList of TextFields to see what is written
            Iterator<TextField> fieldIterator = triggerFields.iterator();
            Iterator<String> variableNameIterator = triggersVariablesLists.get(selectedTriggerType).iterator();

            while (fieldIterator.hasNext() && variableNameIterator.hasNext())
                variables.put(variableNameIterator.next(), fieldIterator.next().getText());

        }
        clearTriggerFields();
        return TriggerBuilder.build(selectedTriggerType, variables);
    }



    private void clearTriggerFields() {
        for(TextField field : triggerFields)
            field.clear();

        startTriggerField.reset();
        fireTime.reset();
        repeatingIntervalField.clear();
    }

    public VBox getvBoxForTriggerTextFields() {return vBoxForTriggerTextFields;}


    public boolean areAllFieldsEmpty() {
        if(selectedTriggerType.equals("RepeatingTimeTrigger")){
            return (repeatingIntervalField.getText().isEmpty() || repeatingUnitBox.getValue().isEmpty() && startTriggerField.isEmpty()) ;
        }
        else if (selectedTriggerType.equals("NonRepeatingTimeTrigger")){
            return fireTime.isEmpty();
        }
        else{
            if(triggerFields.isEmpty()){return false;} //if there are no fields at all
            for(TextField field : triggerFields)
                if(!field.getText().isEmpty())
                    return false;
            return true;
        }
    }
}
