/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package newton;

import newton.modules.TimeTrigger;
import newton.modules.reactions.*;
// import newton.modules.triggers.*;
import newton.modules.Rule;
import newton.interfaces.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 *
 * @author pxlman
 */


public class Newton {

    public static void main(String[] args) {
		Date trigger_date = new Date();
		String repeating_unit = "s";
		int repeating_interval = 20;

		Rule rule = new Rule();

		String filepath = "C:\\Users\\1way\\Documents\\hello.txt";
		rule.setStartLife(LocalDateTime.now());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

//		IReaction reaction = new FileOpenerReaction(filepath);
//		IReaction reaction = new CommandExecutorReaction("notepad");
		IReaction reaction = new Notification("Thekr", "Othker Allah");

		ITrigger trigger = new TimeTrigger(LocalDateTime.now(), TimeUnit.SECONDS, 8);

		rule.addTrigger(trigger);
		rule.addReaction(reaction);

		System.out.println(LocalDateTime.now().format(formatter));
		rule.setEndLife(LocalDateTime.now().plusMinutes(1));

		while(true) {
			rule.apply();
		}

    }
}


