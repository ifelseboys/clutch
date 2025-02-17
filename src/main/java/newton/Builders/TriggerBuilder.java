package newton.Builders;

import newton.interfaces.ITrigger;
import newton.Exceptions.InvalidTriggerInformation;
import newton.modules.triggers.MachineStartTrigger;
import newton.modules.triggers.TimeTrigger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TriggerBuilder {

    public static ITrigger build(String triggerType, HashMap<String, String> variables) throws InvalidTriggerInformation {
        try{
            if(triggerType.equals("TimeTrigger")) {
                return buildTimeTrigger(variables);
            }
            else if (triggerType.equals("MachineStartTrigger")) {
                return buildMachineStartTrigger(variables);
            }
            return null; //should never happen though
        }
        catch (Exception e){
            throw new InvalidTriggerInformation();
        }
    }

    public static ITrigger buildTimeTrigger(HashMap<String, String> variables) throws InvalidTriggerInformation {
        //check the input validity
        try{
            LocalDateTime x = LocalDateTime.parse(variables.get("startTime"));
            TimeUnit y = TimeUnit.valueOf(variables.get("timeUnit"));
            int z = Integer.parseInt(variables.get("repeatingInterval"));
            return new TimeTrigger(x,y,z);
        }
        catch(Exception e){
            throw new InvalidTriggerInformation();
        }
    }

    public static ITrigger buildMachineStartTrigger(HashMap<String, String> variables) throws InvalidTriggerInformation {
        if(!variables.isEmpty())
            throw new InvalidTriggerInformation();
        return new MachineStartTrigger();
    }
}
