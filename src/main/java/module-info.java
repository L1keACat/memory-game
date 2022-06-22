module com.edu.memorygame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.edu.memorygame to javafx.fxml;
    exports com.edu.memorygame;
    exports com.edu.memorygame.controller;
    opens com.edu.memorygame.controller to javafx.fxml;
}