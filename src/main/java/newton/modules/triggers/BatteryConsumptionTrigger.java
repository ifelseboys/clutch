package newton.modules.triggers;

import newton.interfaces.ITrigger;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;

import java.time.LocalDateTime;
import java.util.List;


public class BatteryConsumptionTrigger implements ITrigger {
    static private final SystemInfo systemInfo = new SystemInfo();
    static private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private int levelOfConsumption = 100;
    private LocalDateTime lastFireTime = LocalDateTime.now();

    public BatteryConsumptionTrigger() {}
    public BatteryConsumptionTrigger(int levelOfConsumption) {
        this.levelOfConsumption = levelOfConsumption;
    }

    @Override
    public boolean checkTrigger() {
        List<PowerSource> powerSources = hardware.getPowerSources();
        if(powerSources.isEmpty()) {
            return false;
        }


        long remaining = 0;
        long total = 0;
        for (PowerSource battery : powerSources) {
            remaining += (long) (battery.getRemainingCapacityPercent() * battery.getCurrentCapacity());
            total += battery.getCurrentCapacity();
        }
        long usage = 100 - (total * 100 / remaining);

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
