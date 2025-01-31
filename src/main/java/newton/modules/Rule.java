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
	long id;
	LocalDateTime start_life;
	LocalDateTime end_life;
	Set<ITrigger> triggers;
	Set<IReaction> reactions;

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
	public void apply(){
		for(ITrigger trigger :triggers){
			if(!trigger.isTriggered()){
				return ;
			}
		}
		for(IReaction reaction: reactions){
			reaction.react();
		}
	}
}
