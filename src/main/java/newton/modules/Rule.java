package newton.modules;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pxlman
 */


import newton.interfaces.IReaction;
import newton.interfaces.ITrigger;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Rule {

	long id;
	static long idCounter = 0;
	LocalDateTime start_life;
	LocalDateTime expirationDate;
	ArrayList<ITrigger> triggers;
	ArrayList<IReaction> reactions;


	public Rule(LocalDateTime start_life, LocalDateTime expirationDate, ArrayList<ITrigger> triggers, ArrayList<IReaction> reactions) {
		this.start_life = start_life;
		this.expirationDate = expirationDate;
		this.triggers = triggers;
		this.reactions = reactions;
		id = idCounter++;
	}

	public Rule(){
		//TODO : by default we add a trigger, that it's job is to add the Rule to the data base at the start_time
		//TODO : by default we add a trigger, that it's job is delete the Rule itself from the database
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

		// Check if a trigger is not true to return
		for(ITrigger trigger : triggers){
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
