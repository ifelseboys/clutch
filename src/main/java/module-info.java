module Clutch {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.net.http;
    requires com.github.oshi;

    opens Clutch to javafx.fxml;
    opens Clutch.modules to com.fasterxml.jackson.databind;
    opens Clutch.modules.reactions to com.fasterxml.jackson.databind;
    opens Clutch.modules.triggers to com.fasterxml.jackson.databind;
    opens Clutch.interfaces to com.fasterxml.jackson.databind;


    exports Clutch.interfaces to com.fasterxml.jackson.databind;
    exports Clutch.modules.reactions to com.fasterxml.jackson.databind;
    exports Clutch.modules.triggers to com.fasterxml.jackson.databind;
    exports Clutch.modules to com.fasterxml.jackson.databind;
    exports Clutch;
    exports Clutch.Controllers;
    opens Clutch.Controllers to javafx.fxml;
}