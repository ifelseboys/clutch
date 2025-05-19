package Clutch.modules;

import Clutch.Main;
import Clutch.interfaces.IReaction;
import Clutch.interfaces.ITrigger;
import Clutch.modules.triggers.NonRepeatingTimeTrigger;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Rule {

	private long id;
	private static long idCounter = 0;
	private LocalDateTime start_life;
	private LocalDateTime expirationDate;
	private ArrayList<ITrigger> triggers;
	private ArrayList<IReaction> reactions;


	public Rule(LocalDateTime start_life, LocalDateTime expirationDate, ArrayList<ITrigger> triggers, ArrayList<IReaction> reactions) {
		this.start_life = start_life;
		this.expirationDate = expirationDate;
		this.triggers = triggers;
		this.reactions = reactions;
		id = idCounter++;
	}

	public Rule(){
		triggers = new ArrayList<>();
		reactions = new ArrayList<>();
	}


	//setters
	public void setId(long id){
		this.id = id;
	}
	public void setStartLife(LocalDateTime t){
		this.start_life = t;
	}
	public void setEndLife(LocalDateTime t){
		this.expirationDate = t;
	}
	public void setTriggers(ArrayList<ITrigger> triggers) {this.triggers = triggers;}
	public void setReactions(ArrayList<IReaction> reactions) {this.reactions = reactions;}
	public void setIdCounter(long idCounter) {this.idCounter = idCounter;}

	//getters
	public long getId() {return id;}
	public long getIdCounter() {return idCounter;}
	public ArrayList<ITrigger> getTriggers() {return triggers;}
	public ArrayList<IReaction> getReactions() {return reactions;}
	public LocalDateTime getStart_life() {return start_life;}
	public LocalDateTime getExpirationDate() {return expirationDate;}


	public void addTrigger(ITrigger trigger){triggers.add(trigger);}
	public void addReaction(IReaction reaction){reactions.add(reaction);}
	public void removeTrigger(ITrigger trigger){triggers.remove(trigger);}
	public void removeReaction(IReaction reaction){
		reactions.remove(reaction);
	}


	public void apply(){
		if(!start_life.isBefore(LocalDateTime.now())) //I am not even born yet !
			return;

		if(expirationDate.isBefore(LocalDateTime.now())){ //my time has come
			Main.deleteRule((int) id);
			return;
		}

		// Check if a trigger is not true to return
		for(ITrigger trigger : triggers){
			if(trigger instanceof NonRepeatingTimeTrigger) //delete non repeating time trigger after it being fired
				if(((NonRepeatingTimeTrigger) trigger).isFired())
					Main.deleteRule((int) id);


			if(!trigger.checkTrigger()){
				return;
			}
		}

		// if the constraints are okay then run the reactions
		for(IReaction reaction: reactions){
			reaction.react();
		}
	}


	boolean equals(Rule rule){
		return (id == rule.id);
	}
}
