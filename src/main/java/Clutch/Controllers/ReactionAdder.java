package Clutch.Controllers;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import Clutch.Builders.RactionBuilder;
import Clutch.interfaces.IReaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ReactionAdder {

    String selectedReactionType = "";
    Font textFont = Font.font("Arial", FontWeight.BOLD,16);
    Font inputFont = Font.font("Arial", FontWeight.NORMAL,13);
    private ArrayList<Text> reactionTexts = new ArrayList<>();

    private ArrayList<TextField> reactionFields = new ArrayList<>();



    VBox vBoxForReactionTextFields = new VBox(14);



    HashMap<String, ArrayList<String>> reactionsVariablesLists;

    public VBox createReactionVBox(){
        clearReactionFields();
        vBoxForReactionTextFields.getChildren().clear();
        vBoxForReactionTextFields.setPadding(new Insets(0,0,0,10));


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
        return vBoxForReactionTextFields;
    }


    public IReaction getReaction() throws Exception {
        HashMap<String, String> variables = new HashMap<>();

        Iterator<TextField> fieldIterator = reactionFields.iterator();
        Iterator<String> variableNameIterator = reactionsVariablesLists.get(selectedReactionType).iterator();

        while(fieldIterator.hasNext() && variableNameIterator.hasNext())
            variables.put(variableNameIterator.next(), fieldIterator.next().getText());

        clearReactionFields();
        return RactionBuilder.build(selectedReactionType, variables);
    }


    private void clearReactionFields() {
        for (TextField field : reactionFields)
            field.clear();
    }

    public void setReactionsVariablesLists(HashMap<String, ArrayList<String>> reactionsVariablesLists) {this.reactionsVariablesLists = reactionsVariablesLists;}
    public void setSelectedReactionType(String selectedReactionType) {this.selectedReactionType = selectedReactionType;}
    public VBox getvBoxForReactionTextFields() {return vBoxForReactionTextFields;}

    public boolean areAllFieldsEmpty(){
        if(reactionFields.isEmpty()){return false;}
        for(TextField field : reactionFields)
            if (!field.getText().isEmpty())
                return false;
        return true;
    }
}
