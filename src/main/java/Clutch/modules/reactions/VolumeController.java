package Clutch.modules.reactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import Clutch.interfaces.IReaction;
import java.io.IOException;

@JsonTypeName("VolumeController")
public class VolumeController implements IReaction {
    int volumeLevel;

    public VolumeController(int volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public VolumeController() {
    }

    static String decreaseCommand = "powershell -Command \"(New-Object -ComObject WScript.Shell).SendKeys([char]174)\"";
    static String increaseCommand = "powershell -Command \"(New-Object -ComObject WScript.Shell).SendKeys([char]175)\"";
    static String muteCommand = "powershell -Command \"(New-Object -ComObject WScript.Shell).SendKeys([char]173)\"";

    @Override
    public void react() {
        String os = System.getProperty("os.name").toLowerCase();
        try{
            if(os.contains("windows")) {
                adjustVolumeForWindows();
            }
            else if(os.contains("linux")) {
                adjustVolumeForLinux();
            }
        }
        catch(Exception e){
            throw new RuntimeException("could not adjust volume level");
        }
    }

    public void adjustVolumeForWindows() throws IOException {
        if(volumeLevel == 0){
            Runtime.getRuntime().exec(muteCommand);
            return;
        }
        setVolumeToZero();
        for(int i = 0; i < volumeLevel / 2; i++){
            Runtime.getRuntime().exec(increaseCommand);
        }

    }

    public void adjustVolumeForLinux() throws IOException {
        String command = "amixer set Master " + volumeLevel + '%';
        Runtime.getRuntime().exec(command);
    }

    public void setVolumeToZero() throws IOException {
        for(int i = 0 ; i < 51 ; i++){
            Runtime.getRuntime().exec(decreaseCommand);
        }
    }

    public int getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(int volumeLevel) {
        this.volumeLevel = volumeLevel;
    }
}
