package Clutch.interfaces;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import Clutch.modules.reactions.*;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)

//tell json about the reactions
@JsonSubTypes(
		{
			@JsonSubTypes.Type(value = CommandExecutor.class, name = "commandExecutor"),
			@JsonSubTypes.Type(value = FileOpener.class, name = "fileOpener"),
			@JsonSubTypes.Type(value = Notification.class, name = "notification"),
			@JsonSubTypes.Type(value = ProcessKiller.class, name = "ProcessKiller"),
			@JsonSubTypes.Type(value =  BrightnessController.class, name = "BrightnessController"),
			@JsonSubTypes.Type(value = DeviceShutDown.class, name = "DeviceShutDown"),
			@JsonSubTypes.Type(value = FileCloser.class, name = "FileCloser"),
			@JsonSubTypes.Type(value = VolumeController.class, name = "VolumeController"),
			@JsonSubTypes.Type(value = VerseFromQuran.class, name = "VerseFromQuran")
		}
)

public interface IReaction {
	public void react();
}


