module umg.base_de_datos.proyecto_3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens umg.base_de_datos.proyecto_3 to javafx.fxml;
    exports umg.base_de_datos.proyecto_3;
}