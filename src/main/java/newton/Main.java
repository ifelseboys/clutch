package newton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import newton.modules.JSONDatabase;
import newton.modules.Rule;

import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        rules = new CopyOnWriteArrayList<>(database.getRules());
        Thread thread = new Thread(Main::runRules);
        thread.start();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Reactron");
        stage.show();
    }

    private static CopyOnWriteArrayList<Rule> rules = new CopyOnWriteArrayList<>();
    private static JSONDatabase database = new JSONDatabase();

    private static void runRules() {
        int sleepInterval = 90;
        while(true){
            for(Rule rule : rules){
                rule.apply();
            }

            try {sleep(sleepInterval);}
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    public static void addRule(Rule rule) {
        rules.add(rule);
        database.updateRules(rules);
    }
}
