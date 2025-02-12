/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package newton;

import newton.interfaces.IDatabase;
import newton.interfaces.IReaction;
import newton.interfaces.ITrigger;
import newton.modules.JSONDatabase;
import newton.modules.triggers.*;
import newton.modules.reactions.*;
import newton.modules.Rule;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


/**
 *
 * @author pxlman
 */



public class Newton {

    public static void main(String[] args) {

		IReaction reaction = new Notification("Attention Here", "Othker Allah");
		ITrigger trigger = new TimeTrigger(LocalDateTime.now(), TimeUnit.SECONDS, 10);


		Rule rule = new Rule();
		rule.setStartLife(LocalDateTime.now());
		rule.setEndLife(LocalDateTime.now().plusMinutes(20));
		rule.addTrigger(trigger);
		rule.addReaction(reaction);



		ArrayList<Rule> rules = new ArrayList<Rule>();
		IDatabase database = new JSONDatabase();
//        rules.add(rule);
//        database.updateRules(rules);
        rules = database.getRules();

		while(true) {
            for (Rule value : rules)
                value.apply();
		}

    }
}