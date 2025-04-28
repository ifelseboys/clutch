package Clutch.modules.triggers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import Clutch.interfaces.ITrigger;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

import java.time.LocalDateTime;

@JsonTypeName("MemoryConsumptionTrigger")
public class MemoryConsumptionTrigger implements ITrigger {

    static private final SystemInfo systemInfo = new SystemInfo();
    static private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private int levelOfConsumption = 100;
    private LocalDateTime lastFireTime = LocalDateTime.now();

    public MemoryConsumptionTrigger() {}
    public MemoryConsumptionTrigger(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }

    @Override
    public boolean checkTrigger() {
        GlobalMemory memory = hardware.getMemory();
        double temp = ((double)memory.getAvailable() / memory.getTotal() ) * 100;
        int usedMemoryPercentage =  100 - (int)temp;
        if(usedMemoryPercentage >= levelOfConsumption){
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
