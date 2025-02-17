module newton {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.annotation;

    //you may add some libraries here like : org.kordamp.bootstrapfx.core;
    //for the css stuff

    opens newton to javafx.fxml;
    exports newton;
}