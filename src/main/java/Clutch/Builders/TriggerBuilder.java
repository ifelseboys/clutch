package Clutch.Builders;
import Clutch.interfaces.ITrigger;
import Clutch.modules.triggers.*;
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
        else if (triggerType.equals("DiskConsumptionTrigger")) {
            return buildDiskConsumptionTrigger(variables);
        }
        else if (triggerType.equals("BatteryConsumptionTrigger")) {
            buildBatteryConsumptionTrigger(variables);
        }
        else if (triggerType.equals("WindowContainingWordTrigger")) {
            return buildWindowContainingWordTrigger(variables);
        }
        return null; //should never happen though
    }

    public static ITrigger buildTimeTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        //check the input validity
        try{
            LocalDateTime x = LocalDateTime.parse(variables.get("startTime"));
            TimeUnit y = TimeUnit.valueOf(variables.get("repeatingUnit"));
            Integer z = Integer.parseInt(variables.get("repeatingInterval"));
            if(z != null && z <= 0)
                throw new IllegalArgumentException("repeating Interval must be greater than zero");

            return new TimeTrigger(x,y,z);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Invalid time trigger values");
        }
    }

    public static ITrigger buildMachineStartTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        if(!variables.isEmpty())
            throw new IllegalArgumentException("Machine Start Trigger doesn't have variables");
        return new MachineStartTrigger();
    }


    public static ITrigger buildMemoryConsumptionTrigger(HashMap<String, String> variables){
        if(variables.isEmpty())
            throw new IllegalArgumentException("Memory Consumption Trigger has empty level of Consumption");

        Integer levelOfConsumption = Integer.parseInt(variables.get("levelOfConsumption"));
        if(levelOfConsumption < 0 || levelOfConsumption > 100)
            throw new IllegalArgumentException("level of Consumption should be between 0 and 100");

        return new MemoryConsumptionTrigger(levelOfConsumption);
    }


    public static ITrigger buildCPUConsumptionTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty())
            throw new IllegalArgumentException("CPU Consumption Trigger has empty level of Consumption");
        Integer levelOfConsumption = Integer.parseInt(variables.get("levelOfConsumption"));
        if(levelOfConsumption < 0 || levelOfConsumption > 100)
            throw new IllegalArgumentException("level of Consumption should be between 0 and 100");

        return new CPUConsumptionTrigger(levelOfConsumption);
    }

    public static ITrigger buildDiskConsumptionTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty()) throw new IllegalArgumentException("Disk consumption Trigger has empty level of Consumption");
        int levelOfConsumption = Integer.parseInt(variables.get("levelOfConsumption"));
        if(levelOfConsumption < 0 || levelOfConsumption > 100)
            throw new IllegalArgumentException("level of Consumption should be between 0 and 100");

        return new DiskConsumptionTrigger(levelOfConsumption);
    }

    public static ITrigger buildBatteryConsumptionTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty()) throw new IllegalArgumentException("Battery Consumption Trigger has an empty level");
        int levelOfConsumption = Integer.parseInt(variables.get("levelOfConsumption"));
        if(levelOfConsumption < 0 || levelOfConsumption > 100)
            throw new IllegalArgumentException("level of Consumption should be between 0 and 100");

        return new BatteryConsumptionTrigger(levelOfConsumption);
    }

    public static ITrigger buildWindowContainingWordTrigger(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty())
            throw new IllegalArgumentException("Window Containing Word Trigger has empty variables");
        return new WindowContainingWordTrigger(variables.get("targetWord"));
    }

}
