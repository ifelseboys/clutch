package newton.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import newton.modules.triggers.CPUConsumptionTrigger;
import newton.modules.triggers.MachineStartTrigger;
import newton.modules.triggers.MemoryConsumptionTrigger;
import newton.modules.triggers.TimeTrigger;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)

@JsonSubTypes({
		//tell json about the trigger types:
		@JsonSubTypes.Type(value = TimeTrigger.class, name = "time"),
		@JsonSubTypes.Type(value = MachineStartTrigger.class, name = "machineStart"),
		@JsonSubTypes.Type(value = MemoryConsumptionTrigger.class, name = "MemoryConsumptionTrigger"),
		@JsonSubTypes.Type(value = CPUConsumptionTrigger.class, name = "CPUConsumptionTrigger")
})

public interface ITrigger {
	boolean checkTrigger();
}
