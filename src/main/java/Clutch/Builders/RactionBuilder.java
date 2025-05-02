package Clutch.Builders;

import Clutch.interfaces.IReaction;
import Clutch.modules.reactions.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class RactionBuilder {
    public static IReaction build(String reactionType, HashMap<String, String> variables) throws IllegalArgumentException {
        try{
            if(reactionType.equals("CommandExecutor")){
                return buildCommandExecutor(variables);
            }
            else if (reactionType.equals("FileOpener")){
                return buildFileOpener(variables);
            }
            else if (reactionType.equals("Notification")){
                return buildNotification(variables);
            }
            else if (reactionType.equals("ProcessKiller")){
                return buildProcessKiller(variables);
            }
            else if (reactionType.equals("BrightnessController")){
                return buildBrightnessController(variables);
            }
            else if (reactionType.equals("DeviceShutDown")){
                return buildDeviceShutDown(variables);
            }
            else if (reactionType.equals("FileCloser")){
                return buildFileCloser(variables);
            }
            else if (reactionType.equals("VolumeController")){
                return buildVolumeController(variables);
            }
            else if (reactionType.equals("VerseFromQuran")){
                return buildVerseFromQuranReaction(variables);
            }
            else{
                return null;
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException("Invalid reaction type");
        }
    }


    public static IReaction buildCommandExecutor(HashMap<String, String> variables) {
        String command = variables.get("command");
        if(isCommandValid(command)){
            return new CommandExecutor(command);
        }
        else{
            throw new IllegalArgumentException("Invalid command");
        }
    }

    public static IReaction buildFileOpener(HashMap<String, String> variables) throws IllegalArgumentException {
        String filePath = variables.get("filePath");
        if(isValidFilePath(filePath)){
            return new FileOpener(filePath);
        }
        else{
            throw new IllegalArgumentException("Invalid file path");
        }
    }

    public static IReaction buildNotification(HashMap<String, String> variables) throws IllegalArgumentException {
        String title = variables.get("title");
        String message = variables.get("message");
        return new Notification(title, message);
    }

    public static IReaction buildProcessKiller(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty()){
            throw new IllegalArgumentException("Empty variables");
        }
        String processName = variables.get("processName");
        String os = System.getProperty("os.name").toLowerCase();
        try {
            List<Long> pids = ProcessKiller.getPidsByName(processName, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid process name");
        }
        return new ProcessKiller(processName);
    }

    public static IReaction buildBrightnessController(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty()){
            throw new IllegalArgumentException("Empty variables");
        }
        int percent = Integer.parseInt(variables.get("percent"));
        return new BrightnessController(percent);
    }

    public static IReaction buildDeviceShutDown(HashMap<String, String> variables) throws IllegalArgumentException {
        return new DeviceShutDown();
    }

    public static IReaction buildFileCloser(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty())
            throw new IllegalArgumentException("Empty variables");

        if(!isValidFilePath(variables.get("filePath")))
            throw new IllegalArgumentException("Invalid file path");

        return new FileCloser(variables.get("filePath"));
    }

    public static IReaction buildVolumeController(HashMap<String, String> variables) throws IllegalArgumentException {
        if(variables.isEmpty())
            throw new IllegalArgumentException("Empty variables");
        int volumeLevel = Integer.parseInt(variables.get("volumeLevel"));
        if(volumeLevel < 0 || volumeLevel > 100){
            throw new IllegalArgumentException("Invalid volume level");
        }
        return new VolumeController(volumeLevel);
    }

    public static IReaction buildVerseFromQuranReaction(HashMap<String, String> variables) throws IllegalArgumentException {
        return new VerseFromQuran();
    }

    private static boolean isValidFilePath(String path) {
        if (path == null || path.isEmpty())
            return false; // Null or empty path is invalid


        File file = new File(path);
        return file.exists() && file.isFile(); // Check if the file exists and is a regular file
    }

    private static boolean isCommandValid(String command) {
        try {
            String os = System.getProperty("os.name");
            int exitCode = 0;
            if (os.toLowerCase().contains("windows")) {
                Process process = Runtime.getRuntime().exec("where " + command);
                exitCode = process.waitFor();
            }
            else if (os.toLowerCase().contains("linux")) {
                Process process = Runtime.getRuntime().exec("which " + command);
                exitCode = process.waitFor();
            }
            if (exitCode != 0) {return false;}
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

