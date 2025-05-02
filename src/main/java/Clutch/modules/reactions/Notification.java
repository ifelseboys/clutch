package Clutch.modules.reactions;

import Clutch.interfaces.IReaction;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeName("notification")
@JsonPropertyOrder({"title", "message"})
public class Notification implements IReaction {
    private String title;
    private String message;

    private String windowsCommand;

    public Notification() {
        title = "";
        message = "";
    }

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
        if(windowsCommand == null || windowsCommand.isEmpty()){
            windowsCommand = "powershell.exe -Command \""
                    + "[Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType = WindowsRuntime]; "
                    + "$template = [Windows.UI.Notifications.ToastNotificationManager]::GetTemplateContent([Windows.UI.Notifications.ToastTemplateType]::ToastText02); "
                    + "$texts = $template.GetElementsByTagName('text'); "
                    + "$texts.Item(0).InnerText = '{0}'; "
                    + "$texts.Item(1).InnerText = '{1}'; "
                    + "$toast = [Windows.UI.Notifications.ToastNotification]::new($template); "
                    + "$notifier = [Windows.UI.Notifications.ToastNotificationManager]::CreateToastNotifier('Notification Script'); "
                    + "$notifier.Show($toast)\"";
        }
        windowsCommand = windowsCommand.replace("{0}", title);
        windowsCommand = windowsCommand.replace("{1}", message);
    }

    @Override
    public void react() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            show_windows_notification();
        }
        else if (os.toLowerCase().contains("linux")) {
            show_linux_notification();
        }
    }


    void show_windows_notification() {
        // we are going to pass the command to the powershell
        try{
            Runtime.getRuntime().exec(windowsCommand);
        }
        catch(Exception e){
            System.out.println("error in the notification reaction, in windows\n");
        }
    }

    void show_linux_notification() {
        try{
            Runtime.getRuntime().exec(new String[]{"notify-send", title, message});
        }
        catch(Exception e){
            System.out.println("error in the notification reaction, in Linux\n");
        }
    }

    //getters
    public String getTitle() {return title;}
    public String getMessage() {return message;}
    public String getWindowsCommand() {return windowsCommand;}

    //setters
    public void setTitle(String title) {this.title = title;}
    public void setMessage(String message) {this.message = message;}
    public void setWindowsCommand(String windowsCommand) {this.windowsCommand = windowsCommand;}
}