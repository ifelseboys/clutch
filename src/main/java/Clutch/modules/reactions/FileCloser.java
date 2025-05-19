package Clutch.modules.reactions;

import Clutch.interfaces.IReaction;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonTypeName("FileCloser")
public class FileCloser implements IReaction {

    String filePath;

    @Override
    public void react() {
        String osName = System.getProperty("os.name");
        List<Long> PIDS;
        try{
            if(osName.toLowerCase().contains("win")) {
                PIDS = getWindowsPids(filePath);
            }
            else {
                PIDS = getUnixPids(filePath);
            }

            for(Long PID : PIDS) {
                ProcessKiller.killProcess(PID);
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    private static List<Long> getUnixPids(String filePath) throws IOException, InterruptedException {
        List<Long> pids = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("lsof", "-t", filePath);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        long pid = Long.parseLong(line);
                        pids.add(pid);
                    } catch (NumberFormatException e) {
                        // Ignore invalid lines
                    }
                }
            }
        }

        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new IOException("Failed to list processes holding the file. Exit code: " + exitCode);
        }
        return pids;
    }



    private static List<Long> getWindowsPids(String filePath) throws IOException, InterruptedException {
        Set<Long> pids = new HashSet<>(); // Use Set to avoid duplicates
        // Include -accepteula to prevent EULA prompts on first run
        ProcessBuilder pb = new ProcessBuilder("Handle/handle.exe", "-nobanner", "-accepteula", filePath);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        // Regex to match "pid: 1234" or "pid:1234" anywhere in the line
        Pattern pidPattern = Pattern.compile("pid:\\s*(\\d+)");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pidPattern.matcher(line.trim());
                if (matcher.find()) {
                    try {
                        long pid = Long.parseLong(matcher.group(1));
                        pids.add(pid);
                    } catch (NumberFormatException e) {
                        // Ignore invalid PID formats
                    }
                }
            }
        }

        int exitCode = p.waitFor();
        if (exitCode != 0) {
            throw new IOException(
                    "Failed to list processes. Exit code: " + exitCode +
                            ". Ensure handle.exe is installed and accessible."
            );
        }

        return new ArrayList<>(pids); // Return unique PIDs as a List
    }



    public FileCloser(String filePath) {
        this.filePath = filePath;
    }

    public FileCloser() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
