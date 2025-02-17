package newton.interfaces;
import newton.modules.triggers.TimeTrigger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import newton.modules.triggers.MachineStartTrigger;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)

@JsonSubTypes({
		//tell json about the trigger types:
		@JsonSubTypes.Type(value = TimeTrigger.class, name = "time"),
		@JsonSubTypes.Type(value = MachineStartTrigger.class, name = "machineStart"),
})

public interface ITrigger {
	boolean checkTrigger();
}
