package Clutch.modules.triggers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javafx.util.Pair;
import Clutch.interfaces.ITrigger;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;


@JsonTypeName("time")
@JsonPropertyOrder({"id", "idCounter", "repeatingInterval", "repeatingUnit", "startTime"})
public class TimeTrigger implements ITrigger {

    private LocalDateTime startTime;
    private TimeUnit repeatingUnit;
    private int repeatingInterval;
    private int id;
    private static int idCounter = 0;

    static PriorityQueue<Pair<Integer, LocalDateTime>> schedule = new PriorityQueue<>((a, b) -> a.getValue().compareTo(b.getValue()));


    // to set up the tirgger in the scheduler
    public void setUP(){
        schedule.add(new Pair<Integer, LocalDateTime>(id, startTime));
    }

    public TimeTrigger(LocalDateTime startTime, TimeUnit repeatingUnit, int repeatingInterval) {
        this.startTime = startTime;
        this.repeatingUnit = repeatingUnit;
        this.repeatingInterval = repeatingInterval;
        this.id = idCounter++;
        setUP();
    }

    public TimeTrigger(){}
    //setters and getters, just skip

    @Override
    public boolean checkTrigger() {
        if(schedule.isEmpty()) return false;

        if(schedule.peek().getKey().equals(id) && schedule.peek().getValue().isBefore(LocalDateTime.now()) ){ //my time has come
            schedule.poll();
            if(repeatingInterval != 0)//a repeating task
                schedule.add(new Pair<Integer, LocalDateTime> (id, LocalDateTime.now().plus(repeatingInterval, repeatingUnit.toChronoUnit())));

            return true;
        }
        return false;
    }




    //setter just skip
    public void setRepeatingUnit(String repeatingUnit) {this.repeatingUnit = TimeUnit.valueOf(repeatingUnit);} /// just trying to avoid enums for json
    public void setRepeatingInterval(int repeatingInterval) {this.repeatingInterval = repeatingInterval;}
    public void setId(int id) {this.id = id;}
    public static void setIdCounter(int idCounter) {TimeTrigger.idCounter = idCounter;}

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        schedule.add(new Pair<Integer, LocalDateTime> (id, startTime));
    }


    //getters, just skip man
    public LocalDateTime getStartTime() {return startTime;}
    public String getRepeatingUnit() {return repeatingUnit.toString();}
    public int getRepeatingInterval() {return repeatingInterval;}
    public int getId() {return id;}
    public static int getIdCounter() {return idCounter;}

}
