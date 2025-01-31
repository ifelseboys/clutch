package newton.interfaces;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
import java.util.Set;
import newton.modules.Rule;
/**
 *
 * @author pxlman
 */
public interface IDatabase {
	public void addRule(Rule r);
	public Set<Rule> getRules();
	public void replaceRule(Rule r);
	public void deleteRule(Rule r);
}
