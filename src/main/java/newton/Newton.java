/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package newton;

import newton.modules.Rule;
import newton.interfaces.*;
import java.util.Set;

/**
 *
 * @author pxlman
 */
public class Newton {

    public static void main(String[] args) {
	IDatabase db;
	Set<Rule> rules = db.getRules();
	for(Rule rule: rules){
		rule.apply();
	}	

    }
}
