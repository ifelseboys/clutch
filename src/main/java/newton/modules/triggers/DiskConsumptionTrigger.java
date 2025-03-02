package newton.modules.triggers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.ITrigger;

import java.io.File;
import java.time.LocalDateTime;

@JsonTypeName("DiskConsumptionTrigger")
public class DiskConsumptionTrigger implements ITrigger {
    private int levelOfConsumption = 100;
    private LocalDateTime lastFireTime = LocalDateTime.now();

    public DiskConsumptionTrigger() {}
    public DiskConsumptionTrigger(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }

    @Override
    public boolean checkTrigger() {
        long freeSpace = 0;
        long total = 0;
        File[] roots = File.listRoots();
        for (File root : roots) {
            freeSpace += root.getFreeSpace();
            total += root.getTotalSpace();
        }
        long usage = (freeSpace * 100 / total);

        if(usage >= levelOfConsumption){
            if(!lastFireTime.isBefore(LocalDateTime.now().plusSeconds(8))){ //leave 8 second interval to avoid frantic behaviour
                lastFireTime = LocalDateTime.now();
                return true;
            }
        }
        return false;
    }

    public int getLevelOfConsumption() {
        return levelOfConsumption;
    }

    public void setLevelOfConsumption(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }
}
