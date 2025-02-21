package newton.modules.reactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.IReaction;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@JsonTypeName("fileOpener")
public class FileOpener implements IReaction {
    private String file_path;

    public FileOpener(String file_path){
        this.file_path = file_path;
    }
    public void setFilePath(String file_path){
        this.file_path = file_path;
    }

    public FileOpener() {}

    public void react(){
        File file = new File(this.file_path);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
