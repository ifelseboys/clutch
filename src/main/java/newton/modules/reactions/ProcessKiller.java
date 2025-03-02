package newton.modules.reactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.IReaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("ProcessKiller")
public class ProcessKiller implements IReaction {

    String processName;

    public ProcessKiller(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public ProcessKiller() {
    }

    @Override
    public void react() {
        try{
            killProcessesByName(processName);
        }
        catch(Exception e){
            throw new RuntimeException("could not kill process " + processName);
        }
    }

    public static void killProcessesByName(String processName) throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        List<Long> pids = getPidsByName(processName, os);

        if (pids.isEmpty()) {
            throw new RuntimeException("No processes found with name: " + processName);
        }

        for (long pid : pids) {
            killProcess(pid); // Reuse the killProcess method from the PID-based solution
        }
    }

    private static List<Long> getPidsByName(String processName, String os) throws IOException, InterruptedException {
        if (os.contains("win")) {
            return getWindowsPids(processName);
        } else {
            return getUnixPids(processName);
        }
    }

    private static List<Long> getWindowsPids(String processName) throws IOException, InterruptedException {
        List<Long> pids = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("tasklist", "/FI", "IMAGENAME eq " + processName, "/NH");
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length >= 2 && parts[0].equalsIgnoreCase(processName)) {
                    try {
                        long pid = Long.parseLong(parts[1]);
                        pids.add(pid);
                    } catch (NumberFormatException e) {
                        // Ignore invalid lines
                    }
                }
            }
        }

        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new IOException("Failed to list processes on Windows. Exit code: " + exitCode);
        }
        return pids;
    }

    private static List<Long> getUnixPids(String processName) throws IOException, InterruptedException {
        List<Long> pids = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("ps", "-e", "-o", "pid=", "-o", "comm=");
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+", 2);
                if (parts.length >= 2 && parts[1].equals(processName)) {
                    try {
                        long pid = Long.parseLong(parts[0]);
                        pids.add(pid);
                    } catch (NumberFormatException e) {
                        // Ignore invalid lines
                    }
                }
            }
        }

        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new IOException("Failed to list processes on Unix. Exit code: " + exitCode);
        }
        return pids;
    }

    private static void killProcess(long pid) throws IOException, InterruptedException {
        // Reuse the killProcess method from the PID-based solution
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;

        if (os.contains("win")) {
            processBuilder = new ProcessBuilder("taskkill", "/F", "/PID", String.valueOf(pid));
        } else {
            processBuilder = new ProcessBuilder("kill", "-9", String.valueOf(pid));
        }

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Read output (optional, for debugging)
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while (reader.readLine() != null) {}
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to kill process " + pid);
        }
    }


}