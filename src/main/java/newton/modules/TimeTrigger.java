package newton.modules;


import java.util.Date;
import newton.interfaces.ITrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;


public class TimeTrigger implements ITrigger {
    
    Date startTime;
    String repeatingUnit; //y (year), mo (month), w(week), d(day), h(hour), m (minute), s(second)
    int repeatingAmount;
    private boolean is_triggered = false;

    static SchedulerFactory factory =  new StdSchedulerFactory();
    static Scheduler scheduler;
    static int identifier = 1;
    static {
        try {
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }



    public TimeTrigger(Date startTime, String repeatingUnit, int repeatingAmount) {
        this.startTime = startTime;
        this.repeatingUnit = repeatingUnit;
        this.repeatingAmount = repeatingAmount;
        run();
    }

    //setters and getters, just skip
    public boolean isTriggered() {return is_triggered;}
    public void setIsTriggered(boolean isTriggered){this.is_triggered = isTriggered;}


    public void run(){
        TriggerBuilder builder = TriggerBuilder.newTrigger().
                withIdentity("trigger" + identifier).startAt(new Date());

        //based on the unit we will choose the reapeating stuff
        Trigger trigger;
        switch (repeatingUnit) {
            case "y" :
                trigger = builder.withSchedule(calendarIntervalSchedule().withIntervalInYears(repeatingAmount)).build();
            case "mo" :
                trigger = builder.withSchedule(calendarIntervalSchedule().withIntervalInMonths(repeatingAmount)).build();
            case "w" :
                trigger = builder.withSchedule(calendarIntervalSchedule().withIntervalInWeeks(repeatingAmount)).build();
                break;
            case "d" :
                trigger = builder.withSchedule(calendarIntervalSchedule().withIntervalInDays(repeatingAmount)).build();
                break;
            case "h" :
                trigger = builder.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(repeatingAmount)).build();
                break;
            case "m" :
                trigger = builder.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(repeatingAmount)).build();
                break;
            case "s" :
                trigger = builder.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(repeatingAmount)).build();
                break;
            default: //this is not a repeating task
                trigger = builder.build();
        }
        //we want to creat a job, that all it does is calling the function isTriggered
        JobDetail Tswtich = JobBuilder.newJob(TriggerSwitch.class).withIdentity("job" + identifier++).build();
        try {
            scheduler.scheduleJob(Tswtich, trigger);
        }
        catch(SchedulerException se){
            //TODO : change this to something suitable when code actually works
            se.printStackTrace();
        }
    }
}
