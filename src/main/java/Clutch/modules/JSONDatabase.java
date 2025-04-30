package Clutch.modules;
import Clutch.Controllers.SceneManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import Clutch.interfaces.IDatabase;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class JSONDatabase implements IDatabase {
    private File file;
    ObjectMapper mapper = new ObjectMapper();
    private URL filePath = JSONDatabase.class.getClassLoader().getResource("Clutch/Configuration.json");

    public JSONDatabase() {
        file = new File(String.valueOf(filePath)); //open the json file
        if(!file.exists()) {
            try{
                file.createNewFile();
            }
            catch(Exception e){
                SceneManager.showError("Error", "a problem happened with configuration file");
            }
        }
    }


    @Override
    public void updateRules(List<Rule> rules) {
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new SimpleModule());

        try{
            mapper.writeValue(new File(String.valueOf(filePath)), rules);
        }
        catch (Exception e){
            System.out.println("couldn't serialize rule");
            e.printStackTrace();
        }
    }


    @Override
    public List<Rule> getRules() {
        try{
            File file = new File(String.valueOf(filePath));
            if(!file.exists() || file.length() == 0){
                return new ArrayList<Rule>();
            }
        }
        catch (Exception e){return new ArrayList<Rule>();}

        mapper.registerModule(new SimpleModule());
        mapper.registerModule(new JavaTimeModule());
        //the purpose is to read from the file and return the values of rules
        ArrayList<Rule> rules = null;
        try{
            rules = mapper.readValue(new File(String.valueOf(filePath)), new TypeReference<ArrayList<Rule>>() {});
            return rules;
        }
        catch (Exception e){
            System.out.println("couldn't deserialize rules");
            e.printStackTrace();
            throw new RuntimeException("couldn't deserialize rules");
        }
    }

    @Override
    public List<String> getStringRules() {
        ObjectMapper mapper = new ObjectMapper();

        try{
            File file = new File(String.valueOf(filePath));
            if(!file.exists() || file.length() == 0){
                return new ArrayList<String>();
            }
        }
        catch (Exception e){return new ArrayList<String>();}

        try {
            // Read the JSON array as a list of JsonNode objects
            JsonNode rootNode = mapper.readTree(new File(String.valueOf(filePath)));
            List<String> ruleStrings = new ArrayList<>();

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    // Convert each JSON object back to its raw string representation
                    String rawJson = mapper.writeValueAsString(node);
                    ruleStrings.add(rawJson);
                }
            }
            return ruleStrings;
        } catch (Exception e) {
            throw new RuntimeException("couldn't get rules");
        }

    }


}