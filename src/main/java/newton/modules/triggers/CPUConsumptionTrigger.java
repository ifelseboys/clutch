package newton.modules.triggers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.ITrigger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;


@JsonTypeName("CPUConsumptionTrigger")
public class CPUConsumptionTrigger implements ITrigger {

    static private final SystemInfo systemInfo = new SystemInfo();
    static private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private int levelOfConsumption = 100;

    public CPUConsumptionTrigger(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }

    @Override
    public boolean checkTrigger() {
        CentralProcessor processor = hardware.getProcessor();
        double load = processor.getSystemCpuLoad(1000);
        int intLoad = (int) (load * 100);
        return (intLoad >= levelOfConsumption);
    }

    public int getLevelOfConsumption() {
        return levelOfConsumption;
    }

    public void setLevelOfConsumption(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }

}
