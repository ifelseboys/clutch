package newton.interfaces;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


import newton.modules.Rule;

import java.util.List;

/**
 *
 * @author pxlman
 */
public interface IDatabase {
	public void updateRules(List<Rule> rules);
	public List<Rule> getRules();
}
