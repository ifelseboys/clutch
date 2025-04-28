package Clutch.modules.triggers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import Clutch.interfaces.ITrigger;

@JsonTypeName("machineStart")
public class MachineStartTrigger implements ITrigger {

    private boolean is_triggered = false;

    public boolean checkTrigger(){
        if(!is_triggered){
            is_triggered = true;
            return true;
        }else {
            return false;
        }
    }

}
