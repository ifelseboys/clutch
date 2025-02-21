module org.example.learnfxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens newton to javafx.fxml;
    opens newton.modules to com.fasterxml.jackson.databind;
    opens newton.modules.reactions to com.fasterxml.jackson.databind;
    opens newton.modules.triggers to com.fasterxml.jackson.databind;
    opens newton.interfaces to com.fasterxml.jackson.databind;


    exports newton.interfaces to com.fasterxml.jackson.databind;
    exports newton.modules.reactions to com.fasterxml.jackson.databind;
    exports newton.modules.triggers to com.fasterxml.jackson.databind;
    exports newton.modules to com.fasterxml.jackson.databind;
    exports newton;
}