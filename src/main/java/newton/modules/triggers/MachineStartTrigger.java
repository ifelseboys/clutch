package newton.modules.triggers;

import newton.interfaces.ITrigger;

public class MachineStartTrigger implements ITrigger {
    static private boolean is_triggered = false;

    public boolean isTriggered(){
        if(!is_triggered){
            System.out.println("MachineStartTrigger: Machine started");
            is_triggered = true;
            return true;
        }else {
            return false;
        }
    }
}
