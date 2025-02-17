package newton;

import newton.modules.JSONDatabase;
import newton.modules.Rule;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

public class Main {

    private static CopyOnWriteArrayList<Rule> rules = new CopyOnWriteArrayList<>();
    private static JSONDatabase database = new JSONDatabase();

    public static void main(String[] args) {
        rules =  new CopyOnWriteArrayList<Rule> (database.getRules());
        Thread Background = new Thread(Main::runRules);
        Background.start();//run rules forever
        javafx.application.Application.launch(GUI.class, args); //if doesn't work, remove the args :]
    }

    private static void runRules(){ //Run the rules in the background
        int sleepInterval = 90;
        while(true){
            for (Rule rule : rules){
                rule.apply();

                try{sleep(90);}
                catch (InterruptedException e){e.printStackTrace();}
            }

        }
    }

}
