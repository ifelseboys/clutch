package Clutch.modules;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pxlman
 */


import Clutch.Main;
import Clutch.interfaces.IReaction;
import Clutch.interfaces.ITrigger;
import Clutch.modules.reactions.RuleDeleter;
import Clutch.modules.triggers.TimeTrigger;

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
		//by default we add a Rule that deletes the Rule at the expiration date
		//we do this unless expiration date is infinity
		if(!expirationDate.equals(LocalDateTime.MAX)){
			Rule rule = new Rule(LocalDateTime.now(), LocalDateTime.MAX, new ArrayList<>(), new ArrayList<>());

			ITrigger T = new TimeTrigger(expirationDate, null, 0);
			IReaction R = new RuleDeleter((int)id, (int)rule.getId());
			rule.triggers.add(T);
			rule.reactions.add(R);
			Main.addRule(rule);
		}
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
		if(!start_life.isBefore(LocalDateTime.now())) //my time hasn't come yet shefo !
			return;

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
