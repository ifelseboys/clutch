package Clutch.modules.triggers;

import Clutch.interfaces.ITrigger;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDateTime;

@JsonTypeName("NonRepeatingTimeTrigger")
public class NonRepeatingTimeTrigger implements ITrigger {
    boolean fired = false;
    LocalDateTime fireTime;

    public NonRepeatingTimeTrigger(LocalDateTime fireTime) {
        this.fireTime = fireTime;
    }

    public NonRepeatingTimeTrigger() {
    }

    @Override
    public boolean checkTrigger() {
        if(!fired && fireTime.isBefore(LocalDateTime.now())) { //my time has come
            fired = true;
            return true;
        }
        return false;
    }

    public boolean isFired() {return fired;}
    public LocalDateTime getFireTime() {return fireTime;}

    public void setFired(boolean fired) {this.fired = fired;}
    public void setFireTime(LocalDateTime fireTime) {this.fireTime = fireTime;}
}
