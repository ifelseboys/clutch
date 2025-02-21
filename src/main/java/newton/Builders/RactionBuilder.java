package newton.Builders;

import newton.interfaces.IReaction;
import newton.modules.reactions.CommandExecutor;
import newton.modules.reactions.FileOpener;
import newton.modules.reactions.Notification;

import java.io.File;
import java.util.HashMap;

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

