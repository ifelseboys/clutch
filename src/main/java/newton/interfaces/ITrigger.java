package newton.interfaces;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

public interface ITrigger {
	boolean isTriggered();
	public static int compare(ITrigger t1, ITrigger t2) {return 1;} //TODO : may be overrriden in children to avoid duplicate triggers
}
