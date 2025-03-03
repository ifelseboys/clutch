package newton.modules.reactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.IReaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("ProcessKiller")
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
        List<Long> pids = new ArrayList<>();
        ProcessBuilder pb = new ProcessBuilder("handle.exe", "-nobanner", filePath);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("pid:")) {
                    String[] parts = line.split("\\s+");
                    try {
                        long pid = Long.parseLong(parts[1].replace("pid:", ""));
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
