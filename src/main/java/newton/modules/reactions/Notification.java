package newton.modules.reactions;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.IReaction;


@JsonTypeName("notification")
@JsonPropertyOrder({"title", "message"})
public class Notification implements IReaction {
    private String title;
    private String message;

    private static String windowsCommand = "powershell.exe -Command \""
            .concat("[Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType = WindowsRuntime]; ")
            .concat("$template = [Windows.UI.Notifications.ToastNotificationManager]::GetTemplateContent([Windows.UI.Notifications.ToastTemplateType]::ToastText02); ")
            .concat("$texts = $template.GetElementsByTagName('text'); ")
            .concat("$texts.Item(0).InnerText = '{0}'; ")
            .concat("$texts.Item(1).InnerText = '{1}'; ")
            .concat("$toast = [Windows.UI.Notifications.ToastNotification]::new($template); ")
            .concat("$notifier = [Windows.UI.Notifications.ToastNotificationManager]::CreateToastNotifier('Notification Script'); ")
            .concat("$notifier.Show($toast)\"");


    public Notification() {
        title = "hellooooooo";
        message = "wooooooorld";
        setUP();
    } //just for json

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
        windowsCommand = windowsCommand.replace("{0}", title);
        windowsCommand = windowsCommand.replace("{1}", message);
    }


    public void setUP(){
        if(windowsCommand == null || windowsCommand.isEmpty()){
            windowsCommand = "powershell.exe -Command \""
                    .concat("[Windows.UI.Notifications.ToastNotificationManager, Windows.UI.Notifications, ContentType = WindowsRuntime]; ")
                    .concat("$template = [Windows.UI.Notifications.ToastNotificationManager]::GetTemplateContent([Windows.UI.Notifications.ToastTemplateType]::ToastText02); ")
                    .concat("$texts = $template.GetElementsByTagName('text'); ")
                    .concat("$texts.Item(0).InnerText = '{0}'; ")
                    .concat("$texts.Item(1).InnerText = '{1}'; ")
                    .concat("$toast = [Windows.UI.Notifications.ToastNotification]::new($template); ")
                    .concat("$notifier = [Windows.UI.Notifications.ToastNotificationManager]::CreateToastNotifier('Notification Script'); ")
                    .concat("$notifier.Show($toast)\"");
        }
        windowsCommand = windowsCommand.replace("{0}", title);
        windowsCommand = windowsCommand.replace("{1}", message);
        System.out.println("method setUP has been called");
    }

    @Override
    public void react() {
        System.out.println("method react() has been called");

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

    public String getTitle() {return title;}
    public String getMessage() {return message;}
}
