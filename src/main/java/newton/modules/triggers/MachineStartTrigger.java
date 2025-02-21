package newton.modules.triggers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.ITrigger;

@JsonTypeName("machineStart")
public class MachineStartTrigger implements ITrigger {

    static private boolean is_triggered = false;

    public boolean checkTrigger(){
        if(!is_triggered){
            is_triggered = true;
            return true;
        }else {
            return false;
        }
    }

}
