package newton.Builders;


import newton.interfaces.ITrigger;
import newton.modules.triggers.CPUConsumptionTrigger;
import newton.modules.triggers.MachineStartTrigger;
import newton.modules.triggers.MemoryConsumptionTrigger;
import newton.modules.triggers.TimeTrigger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TriggerBuilder {

    public static ITrigger build(String triggerType, HashMap<String, String> variables) throws IllegalArgumentException {
        if(triggerType.equals("TimeTrigger")) {
            return buildTimeTrigger(variables);
        }
        else if (triggerType.equals("MachineStartTrigger")) {
            return buildMachineStartTrigger(variables);
        }
        else if (triggerType.equals("MemoryConsumptionTrigger")) {
            return buildMemoryConsumptionTrigger(variables);
        }
        else if (triggerType.equals("CPUConsumptionTrigger")) {
            return buildCPUConsumptionTrigger(variables);
        }
        return null; //should never happen though
    }

    private static ITrigger buildTimeTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        //check the input validity
        try{
            LocalDateTime x = LocalDateTime.parse(variables.get("startTime"));
            TimeUnit y = TimeUnit.valueOf(variables.get("repeatingUnit"));
            int z = Integer.parseInt(variables.get("repeatingInterval"));
            return new TimeTrigger(x,y,z);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Invalid time trigger values");
        }
    }

    private static ITrigger buildMachineStartTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        if(!variables.isEmpty())
            throw new IllegalArgumentException("Machine Start Trigger doesn't have variables");
        return new MachineStartTrigger();
    }


    private static ITrigger buildMemoryConsumptionTrigger(HashMap<String, String> variables){
        if(variables.isEmpty())
            throw new IllegalArgumentException("Memory Consumption Trigger has empty level of Consumption");
        int levelOfConsumption = Integer.parseInt(variables.get("levelOfConsumption"));
        if(levelOfConsumption < 0 || levelOfConsumption > 100)
            throw new IllegalArgumentException("level of Consumption should be between 0 and 100");

        return new MemoryConsumptionTrigger(levelOfConsumption);
    }


    private static ITrigger buildCPUConsumptionTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty())
            throw new IllegalArgumentException("CPU Consumption Trigger has empty level of Consumption");
        int levelOfConsumption = Integer.parseInt(variables.get("levelOfConsumption"));
        if(levelOfConsumption < 0 || levelOfConsumption > 100)
            throw new IllegalArgumentException("level of Consumption should be between 0 and 100");

        return new CPUConsumptionTrigger(levelOfConsumption);
    }



}
