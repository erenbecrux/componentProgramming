module com.example.myscreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.myscreen to javafx.fxml;
    exports com.example.myscreen;
}