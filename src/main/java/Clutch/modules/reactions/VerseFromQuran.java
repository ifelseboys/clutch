package Clutch.modules.reactions;

import Clutch.interfaces.IReaction;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

@JsonTypeName("VerseFromQuran")
public class VerseFromQuran implements IReaction {

    public VerseFromQuran(){}

    @Override
    public void react() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //exract the surah itself first
            JsonNode rootNode = mapper.readTree(new File("quran_en.json"));
            JsonNode surah = rootNode.get((int) ((Math.random() * 100) % 114));
            JsonNode verses = surah.get("verses");
            int n = (int) ((Math.random() * 100) % verses.size() - 1);
            String message = new String(verses.get(n).get("text").asText() + verses.get(n).get("translation").asText());

            //print it to the screen
            Notification notification = new Notification("VerseFromQuran", message);
            notification.react();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
