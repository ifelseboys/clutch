package newton.modules.reactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.IReaction;

import java.io.*;

@JsonTypeName("BrightnessController")
public class BrightnessController implements IReaction {

    int percent;

    public BrightnessController(int percent) {
        this.percent = percent;
    }

    public BrightnessController() {
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public void react() {
        try{
            setBrightness(percent);
        }
        catch(Exception e){
            throw new RuntimeException("Brightness controller error");
        }
    }

    public static void setBrightness(int percent) throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            setWindowsBrightness(percent);
        } else if (os.contains("linux")) {
            setLinuxBrightness(percent);
        }
        else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }
    }

    private static void setWindowsBrightness(int percent) throws IOException, InterruptedException {
        String command = "powershell (Get-WmiObject -Namespace root/WMI -Class WmiMonitorBrightnessMethods).WmiSetBrightness(1, " + percent + ")";
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    private static void setLinuxBrightness(int percent) throws IOException {
        File backlightDir = new File("/sys/class/backlight");
        File[] interfaces = backlightDir.listFiles();

        if (interfaces == null || interfaces.length == 0) {
            throw new IOException("No backlight interface found");
        }

        File interfaceDir = interfaces[0]; // Picks first interface
        File maxFile = new File(interfaceDir, "max_brightness");
        File brightnessFile = new File(interfaceDir, "brightness");

        int max = Integer.parseInt(readFile(maxFile));
        int brightness = (percent * max) / 100;

        writeFile(brightnessFile, String.valueOf(brightness));
    }

    private static String readFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        }
    }

    private static void writeFile(File file, String value) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(value);
        }
    }

    public static void main(String[] args) {
        try {
            setBrightness(90); // Set to 50% brightness
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}