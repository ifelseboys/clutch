package newton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import newton.modules.JSONDatabase;
import newton.modules.Rule;
import java.time.LocalDateTime;
import java.util.List;
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

        // Icon stuff
        Image icon = new Image(getClass().getResourceAsStream("logo.jpg"));
        stage.getIcons().add(icon);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Clutch");
        stage.show();
    }

    private static CopyOnWriteArrayList<Rule> rules = new CopyOnWriteArrayList<>();
    private static JSONDatabase database = new JSONDatabase();

    private static void runRules() {
        int sleepInterval = 90;
        while(true){
            for(int i = 0; i < rules.size(); i++){
                Rule rule = rules.get(i);

                if(rule.getExpirationDate().isBefore(LocalDateTime.now())){
                    rules.remove(i);
                    i--;
                    continue;
                }

                if(rule.getStart_life().isBefore(LocalDateTime.now())){
                    rule.apply();
                }
            }

            try {sleep(sleepInterval);}
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    public static void addRule(Rule rule) {
        rules.add(rule);
        database.updateRules(rules);
    }

    public static void deleteRule(Rule rule) {
        rules.remove(rule);
        database.updateRules(rules);
    }

    public static void deleteRule(int id){
        for (Rule rule : rules)
            if (rule.getId() == id)
                deleteRule(rule);
    }

    public static List<String> getStringRules(){
        return database.getStringRules();
    }
}
/*
jpackage --input target/ \
        --name Clutch \
        --main-jar myapp.jar \
        --main-class com.example.MainApp \
        --type exe \  # For Windows, use 'deb' for Linux
          --runtime-image path/to/runtime \
        --icon src/main/resources/icon.ico \
        --dest output/
*/