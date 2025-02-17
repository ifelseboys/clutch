package newton.modules.triggers;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.ITrigger;

import javax.annotation.*;


@JsonTypeName("time")
@JsonPropertyOrder({"repeatingInterval", "repeatingUnit", "startTime"})
public class TimeTrigger implements ITrigger {

    private LocalDateTime startTime;
    private TimeUnit repeatingUnit;
    private int repeatingInterval;

    static PriorityQueue<LocalDateTime> schedule = new PriorityQueue<>();


    // to set up the tirgger in the scheduler
    @PostConstruct
    public void setUP(){
        schedule.add(startTime);
    }

    public TimeTrigger(LocalDateTime startTime, TimeUnit repeatingUnit, int repeatingInterval) {
        this.startTime = startTime;
        this.repeatingUnit = repeatingUnit;
        this.repeatingInterval = repeatingInterval;
        setUP();
    }

    public TimeTrigger(){}
    //setters and getters, just skip

    @Override
    public boolean checkTrigger() {
        if(schedule.isEmpty()) return false;

        while(schedule.peek().compareTo(LocalDateTime.now()) <= 0){ //my time has come
            schedule.poll();
            if(repeatingInterval != 0)//a repeating task
                schedule.add(LocalDateTime.now().plus(repeatingInterval, repeatingUnit.toChronoUnit()));

            return true;
        }
        return false;
    }








    //setter just skip
    public void setRepeatingUnit(String repeatingUnit) {this.repeatingUnit = TimeUnit.valueOf(repeatingUnit);} /// just trying to avoid enums for json
    public void setRepeatingInterval(int repeatingInterval) {this.repeatingInterval = repeatingInterval;}
    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}

    //getters, just skip man
    public LocalDateTime getStartTime() {return startTime;}
    public String getRepeatingUnit() {return repeatingUnit.toString();}
    public int getRepeatingInterval() {return repeatingInterval;}



}
