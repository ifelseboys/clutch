package Clutch.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import Clutch.modules.triggers.*;

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
		@JsonSubTypes.Type(value = CPUConsumptionTrigger.class, name = "CPUConsumptionTrigger"),
		@JsonSubTypes.Type(value = DiskConsumptionTrigger.class, name = "DiskConsumptionTrigger"),
		@JsonSubTypes.Type(value = BatteryConsumptionTrigger.class, name = "BatteryConsumptionTrigger"),
		@JsonSubTypes.Type(value = WindowContainingWordTrigger.class, name = "WindowContainingWordTrigger"),
})

public interface ITrigger {
	boolean checkTrigger();
}
