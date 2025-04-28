package Clutch.modules.reactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import Clutch.interfaces.IReaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@JsonTypeName("DeviceShutDown")
public class DeviceShutDown implements IReaction {

    public DeviceShutDown() {
    }


    @Override
    public void react() {
        try{
            shutdown();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String[] command;

        // Determine OS and build command
        if (os.contains("win")) {
            command = new String[]{"shutdown", "/s", "/t", "0"};
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix") || os.contains("mac")) {
            command = new String[]{"shutdown", "-h", "now"};
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }

        // Execute command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);  // Merge error stream with input stream
        Process process = processBuilder.start();

        // Capture command output
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        // Check exit status
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException(
                    "Command failed with exit code " + exitCode + "\nOutput: " + output
            );
        }
    }
}