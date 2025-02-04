/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package newton;

import newton.modules.reactions.*;
// import newton.modules.triggers.*;
import newton.modules.Rule;
import newton.interfaces.*;
import java.util.Set;

/**
 *
 * @author pxlman
 */
public class Newton {

    public static void main(String[] args) {
	// IDatabase db = new SQLiteDatabase();
	// Set<Rule> rules = db.getRules();
	// for(Rule rule: rules){
	// 	rule.apply();
	// }	
	System.out.println("\nHello World");
	IReaction command_executor_reaction = new CommandExecutorReaction("asdlfkjsd");
	command_executor_reaction.react();

	IReaction file_opener_reaction = new FileOpenerReaction("/home/pxlman/sc.c");
	file_opener_reaction.react();

    }
}
