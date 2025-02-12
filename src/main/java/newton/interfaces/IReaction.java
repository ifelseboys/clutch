package newton.interfaces;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import newton.modules.reactions.CommandExecutor;
import newton.modules.reactions.FileOpener;
import newton.modules.reactions.Notification;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)

//tell json about the reactions
@JsonSubTypes({
		@JsonSubTypes.Type(value = CommandExecutor.class, name = "commandExecutor"),
		@JsonSubTypes.Type(value = FileOpener.class, name = "fileOpener"),
		@JsonSubTypes.Type(value = Notification.class, name = "notification")
}
)

public interface IReaction {
	public void react();
}


