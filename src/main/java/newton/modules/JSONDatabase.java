package newton.modules;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import newton.interfaces.IDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


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
    public void updateRules(CopyOnWriteArrayList<Rule> rules) {
        mapper.registerModule(new JavaTimeModule());
        try{
            mapper.writeValue(new File(filePath), rules);
        }
        catch (Exception e){
            System.out.println("couldn't serialize rule");
            e.printStackTrace();
        }
    }


    @Override
    public CopyOnWriteArrayList<Rule> getRules() {

        mapper.registerModule(new SimpleModule());
        mapper.registerModule(new JavaTimeModule());
        //the purpose is to read from the file and return the values of rules
        CopyOnWriteArrayList<Rule> rules = null;
        try{
            rules = mapper.readValue(new File(filePath), new TypeReference<CopyOnWriteArrayList<Rule>>() {});
        }
        catch (Exception e){
            System.out.println("couldn't deserialize rules");
            e.printStackTrace();
        }
        finally{
            return rules;
        }

    }


}
