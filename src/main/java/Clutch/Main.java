package Clutch;

import Clutch.modules.JSONDatabase;
import Clutch.modules.Rule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import static java.lang.Thread.sleep;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        rules = new CopyOnWriteArrayList<>(database.getRules());
        Thread thread = new Thread(Main::runRules);
        thread.start();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
        Parent root = fxmlLoader.load();

        // Icon stuff
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.jpg")));
        stage.getIcons().add(icon);
        createSystemTray(); //in order to let the user exit the program after closing the gui

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Clutch");
        stage.show();
    }

    private static CopyOnWriteArrayList<Rule> rules = new CopyOnWriteArrayList<>();
    private static JSONDatabase database = new JSONDatabase();

    private static void runRules() {
        int sleepInterval = 100;
        while(true){

            for(Rule rule : rules)
                rule.apply();


            try {sleep(sleepInterval);} // be merciful with the cpu (CPUs rights matter)
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

    public static void updateRules(){
        database.updateRules(rules);
    }

    public static List<String> getStringRules(){
        return database.getStringRules();
    }

    public static void createSystemTray() {
        //get the image
        URL imageUrl = Objects.requireNonNull(Main.class.getResource("/icon.jpg"));
        java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(imageUrl);

        //set up the system tray stuff
        SystemTray tray = SystemTray.getSystemTray();
        PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); //the purpose is to close the program
        popup.add(exitItem);
        TrayIcon trayIcon = new TrayIcon(icon, "Clutch", popup);
        trayIcon.setImageAutoSize(true);
        try{
            tray.add(trayIcon);
        }
        catch (AWTException e){
            return;
        }
    }
}