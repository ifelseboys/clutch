package Clutch.modules.reactions;

import Clutch.Main;
import Clutch.interfaces.IReaction;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RuleDeleter")
public class RuleDeleter implements IReaction {
    private int ID;
    private int selfID;
    RuleDeleter() {} //empty constructors for the eyes of json, I hope someday I do it for the eyes of my wife

    public RuleDeleter(int ID, int selfID) {
        this.ID = ID;
        this.selfID = selfID;
    }

    @Override
    public void react() {
        Main.deleteRule(ID);
        Main.deleteRule(selfID);
    }

    //setters and getters for the eyes of json
    public void setID(int ID) {this.ID = ID;}
    public void setSelfID(int selfID) {this.selfID = selfID;}
    public int getSelfID() {return selfID;}
    public int getID() {return ID;}
}
