package newton.modules.triggers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.ITrigger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.time.LocalDateTime;


@JsonTypeName("CPUConsumptionTrigger")
public class CPUConsumptionTrigger implements ITrigger {

    static private final SystemInfo systemInfo = new SystemInfo();
    static private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private int levelOfConsumption = 100;
    private LocalDateTime lastFireTime = LocalDateTime.now();

    public CPUConsumptionTrigger() {}
    public CPUConsumptionTrigger(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }

    @Override
    public boolean checkTrigger() {
        CentralProcessor processor = hardware.getProcessor();
        double load = processor.getSystemCpuLoad(1000);
        int intLoad = (int) (load * 100);
        if(intLoad >= levelOfConsumption){
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
