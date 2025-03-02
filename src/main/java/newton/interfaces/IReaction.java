package newton.interfaces;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import newton.modules.reactions.*;


@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)

//tell json about the reactions
@JsonSubTypes({
		@JsonSubTypes.Type(value = CommandExecutor.class, name = "commandExecutor"),
		@JsonSubTypes.Type(value = FileOpener.class, name = "fileOpener"),
		@JsonSubTypes.Type(value = Notification.class, name = "notification"),
		@JsonSubTypes.Type(value = ProcessKiller.class, name = "ProcessKiller"),
		@JsonSubTypes.Type(value =  BrightnessController.class, name = "BrightnessController"),
		@JsonSubTypes.Type(value = DeviceShutDown.class, name = "DeviceShutDown")
}
)

public interface IReaction {
	public void react();
}


