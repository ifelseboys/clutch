
package newton.modules.reactions;

import java.lang.Exception;
import java.lang.Runtime;

import newton.interfaces.IReaction;

public class AppOpenerReaction implements IReaction {
    private String command;

    public void setAppName(String command){
        this.command = command;
    }

    public AppOpenerReaction(String command) {
        this.command = command;
    }

    public void react() {
        System.out.println("AppOpenerReaction: " + this.command);
        try {
            String[] command = {this.command};
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}