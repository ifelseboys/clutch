package newton.modules;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pxlman
 */
import java.time.LocalDateTime;
import java.util.Set;
import newton.interfaces.IReaction;
import newton.interfaces.ITrigger;

public class Rule {
	// Private
	long id;
	LocalDateTime start_life;
	LocalDateTime end_life;
	Set<ITrigger> triggers;
	Set<IReaction> reactions;

	// Public 
	public Rule(){
	
	}
	public void setId(long id){
		this.id = id;	
	}
	public void setStartLife(LocalDateTime t){
		this.start_life = t;	
	}
	public void setEndLife(LocalDateTime t){
		this.end_life = t;	
	}
	public void addTrigger(ITrigger trigger){
		triggers.add(trigger);
	}
	public void addReaction(IReaction reaction){
		reactions.add(reaction);
	}
	public void removeTrigger(ITrigger trigger){
		triggers.remove(trigger);
	}
	public void removeReaction(IReaction reaction){
		reactions.remove(reaction);
	}
	public void apply(){
		// LocalDateTime.compareTo(obj) returns negative if the first is smaller
		if (LocalDateTime.now().compareTo(start_life) < 0){
			return;
		}	
		// LocalDateTime.compareTo(obj) returns positive if the first is larger
		if (LocalDateTime.now().compareTo(end_life) > 0){
			return;
		}	
		// Check if a trigger is not true to return
		for(ITrigger trigger :triggers){
			if(!trigger.isTriggered()){
				return ;
			}
		}
		// if the constrains are okay then run the reactions
		for(IReaction reaction: reactions){
			reaction.react();
		}
	}
}
