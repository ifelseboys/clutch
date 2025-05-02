package Clutch.modules.triggers;

import Clutch.Main;
import com.fasterxml.jackson.annotation.JsonTypeName;
import Clutch.interfaces.ITrigger;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@JsonTypeName("time")
public class TimeTrigger implements ITrigger {

    private LocalDateTime startTime;
    private TimeUnit repeatingUnit;
    private Integer repeatingInterval;

    public TimeTrigger(){}

    public TimeTrigger(LocalDateTime startTime, TimeUnit repeatingUnit, Integer repeatingInterval) {
        this.startTime = startTime;
        this.repeatingUnit = repeatingUnit;
        this.repeatingInterval = repeatingInterval;
    }

    @Override
    public boolean checkTrigger() {
        if(startTime.isBefore(LocalDateTime.now())){
            if(repeatingInterval != null){//that means it's repeating task
                startTime = LocalDateTime.now().plus(repeatingInterval, repeatingUnit.toChronoUnit());
                Main.updateRules();
            }
            return true;
        }
        return false;
    }


    //setter just skip
    public void setRepeatingUnit(String repeatingUnit) {this.repeatingUnit = TimeUnit.valueOf(repeatingUnit);} /// just trying to avoid enums for json
    public void setRepeatingInterval(Integer repeatingInterval) {this.repeatingInterval = repeatingInterval;}
    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}

    //getters, just skip man
    public LocalDateTime getStartTime() {return startTime;}
    public String getRepeatingUnit() {return repeatingUnit.toString();}
    public Integer getRepeatingInterval() {return repeatingInterval;}
}
