package newton.modules;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import newton.interfaces.ITrigger;


public class TimeTrigger implements ITrigger {
    
    private LocalDateTime startTime;
    private TimeUnit repeatingUnit;
    private int repeatingInterval;
    private boolean is_triggered = false;

    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TimeTrigger(LocalDateTime startTime, TimeUnit repeatingUnit, int repeatingInterval) {
        this.startTime = startTime;
        this.repeatingUnit = repeatingUnit;
        this.repeatingInterval = repeatingInterval;
        setUP();
    }

    //setters and getters, just skip
    public boolean isTriggered() {
        if(is_triggered){
            is_triggered = false;
            return true;
        }
        return false;
    } ///this is overriden from the interface
    public void setIsTriggered(boolean isTriggered){this.is_triggered = isTriggered;}
    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}
    public void setRepeatingInterval(int repeatingInterval) {this.repeatingInterval = repeatingInterval;}
    public void setRepeatingUnit(TimeUnit repeatingUnit) {this.repeatingUnit = repeatingUnit;}

    public void setUP(){
        long initialDelay = startTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() - (System.currentTimeMillis()/1000);
        Runnable mark_triggered = () -> {setIsTriggered(true);}; ///just assuming that this might work lol
        if(repeatingInterval == 0){//that means it's non-recurring obviously
            scheduler.schedule(mark_triggered, initialDelay, TimeUnit.SECONDS); // gonna be a little messy with long periods, but it's acceptable because this is java bro, it's not retarded c++
        }
        else{
            scheduler.scheduleAtFixedRate(mark_triggered, initialDelay, repeatingInterval, repeatingUnit);
        }
    }
}
