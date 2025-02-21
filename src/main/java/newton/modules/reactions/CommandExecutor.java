
package newton.modules.reactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.IReaction;


/// TODO : add the /SCANNOW commmand from the default rules that comes with the program
@JsonTypeName("commandExecutor")
public class CommandExecutor implements IReaction {
    private String command;


    public CommandExecutor(String command) {
        this.command = command;
    }
    public CommandExecutor(){}



    public void setCommand(String command){
        this.command = command;
    }
    public String getCommand(){return this.command;}


    @Override
    public void react() {
        try {
            String[] command = {this.command};
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}