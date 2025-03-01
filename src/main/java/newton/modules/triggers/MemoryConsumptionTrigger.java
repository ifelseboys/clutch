package newton.modules.triggers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.ITrigger;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

@JsonTypeName("MemoryConsumptionTrigger")
public class MemoryConsumptionTrigger implements ITrigger {

    static private final SystemInfo systemInfo = new SystemInfo();
    static private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private int levelOfConsumption = 100;

    public MemoryConsumptionTrigger(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }

    @Override
    public boolean checkTrigger() {
        GlobalMemory memory = hardware.getMemory();
        double temp = ((double)memory.getAvailable() / memory.getTotal() ) * 100;
        int usedMemoryPercentage =  100 - (int)temp;
        return (usedMemoryPercentage >= levelOfConsumption);
    }

    public int getLevelOfConsumption() {
        return levelOfConsumption;
    }
    public void setLevelOfConsumption(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }
}
