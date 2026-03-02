module org.example.puyascreen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.puyascreen to javafx.fxml;
    exports org.example.puyascreen;
}