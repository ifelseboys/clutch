package newton.modules;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TriggerSwitch implements Job {
    private TimeTrigger trigger;
    public TriggerSwitch(TimeTrigger trigger) {
        //I have a pointer to the TimeTrigger object that has created me
        //and since I am a very  cute and kind TriggerSwitch object, I am goint to call function isTriggered

        this.trigger = trigger;
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
        trigger.setIsTriggered(true);
    }
}
