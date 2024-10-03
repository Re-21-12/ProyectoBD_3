package umg.base_de_datos.proyecto_3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/*TODO:
1. Realizar conexion a posgres
2. Crear clase que recorre archivo csv
3. Utilizar archivo csv es mas facil de recorrer y ya dice las columnas
4. Sincronizar datos en archivo con ambas base de datos
5. Mostrar datos en la lista a traves de recorrer un archivo

* */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 500);
        stage.setTitle("Administrador de base de datos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}