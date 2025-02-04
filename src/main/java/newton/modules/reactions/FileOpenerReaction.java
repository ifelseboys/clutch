package newton.modules.reactions;

import newton.interfaces.IReaction;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;

public class FileOpenerReaction implements IReaction {
    private String file_path;

    public FileOpenerReaction(String file_path){
        this.file_path = file_path;
    }
    public void setFilePath(String file_path){
        this.file_path = file_path;
    }

    public void react(){
        File file = new File(this.file_path);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.println("FileOpenerReaction: " + e.getMessage());
        }
    } 
}
