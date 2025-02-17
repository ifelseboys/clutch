package newton.interfaces;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import newton.modules.Rule;
/**
 *
 * @author pxlman
 */
public interface IDatabase {
	public void updateRules(CopyOnWriteArrayList<Rule> rules);
	public CopyOnWriteArrayList<Rule> getRules();
}
