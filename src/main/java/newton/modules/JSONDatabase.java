package newton.modules;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import newton.interfaces.IDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class JSONDatabase implements IDatabase {
    private File file;
    ObjectMapper mapper = new ObjectMapper();
    private String filePath = "JSONDatabase.json";

    public JSONDatabase() {
        file = new File(filePath); //open the json file
        if(!file.exists()) {
            try{
                file.createNewFile();
            }
            catch(Exception e){
                //TODO : change that line to something actually clean
                System.out.println("couldn't create new file");
            }
        }
    }


    @Override
    public void updateRules(List<Rule> rules) {
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new SimpleModule());

        try{
            mapper.writeValue(new File(filePath), rules);
        }
        catch (Exception e){
            System.out.println("couldn't serialize rule");
            e.printStackTrace();
        }
    }


    @Override
    public List<Rule> getRules() {
        try{
            File file = new File(filePath);
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
            rules = mapper.readValue(new File(filePath), new TypeReference<ArrayList<Rule>>() {});
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
            File file = new File(filePath);
            if(!file.exists() || file.length() == 0){
                return new ArrayList<String>();
            }
        }
        catch (Exception e){return new ArrayList<String>();}

        try {
            // Read the JSON array as a list of JsonNode objects
            JsonNode rootNode = mapper.readTree(new File("JSONDatabase.json"));
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