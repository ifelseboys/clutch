package newton.modules.triggers;


import com.fasterxml.jackson.annotation.JsonTypeName;
import newton.interfaces.ITrigger;

import java.awt.*;

@JsonTypeName("WindowContainingWordTrigger")
public class WindowContainingWordTrigger implements ITrigger {
    String targetWord;

    public WindowContainingWordTrigger(String targetWord) {
        this.targetWord = targetWord.toLowerCase();
    }

    public WindowContainingWordTrigger() {
    }

    @Override
    public boolean checkTrigger() {
        for (Window window : Window.getWindows()) {
            String title = null;

            if (window instanceof Frame) {
                title = ((Frame) window).getTitle();
            } else if (window instanceof Dialog) {
                title = ((Dialog) window).getTitle();
            }

            if (title != null && title.toLowerCase().contains(targetWord)) {
                return true;
            }
        }
        return false;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }
}
