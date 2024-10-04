package umg.base_de_datos.proyecto_3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umg.base_de_datos.proyecto_3.classes.Empleado;
import umg.base_de_datos.proyecto_3.classes.MySQLDatabaseStrategy;
import umg.base_de_datos.proyecto_3.classes.PostgresDatabaseStrategy;
import umg.base_de_datos.proyecto_3.services.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField inputField;

    private DatabaseService dbService;

    @FXML
    public void onMySQLButtonClick() {
        dbService = new DatabaseService(new MySQLDatabaseStrategy());
        dbService.connect();
    }

    @FXML
    public void onPostgresButtonClick() {
        dbService = new DatabaseService(new PostgresDatabaseStrategy());
        dbService.connect();
    }

    @FXML
    public void onInsertButtonClick() {
        String data = inputField.getText();
        Empleado empleado = parseEmpleado(data);
        try {
            dbService.insert(empleado);
            listView.getItems().add(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteButtonClick() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            dbService.delete(selectedItem);
            listView.getItems().remove(selectedItem);
        }
    }

    @FXML
    public void onUpdateButtonClick() {
        String data = inputField.getText();
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Empleado empleado = parseEmpleado(data);
            try {
                dbService.update(empleado);
                listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onSynchronizeButtonClick() {
        // Lógica para sincronizar tablas entre las dos bases de datos
    }

    private Empleado parseEmpleado(String data) {
        String[] datos = data.split(",");
        Empleado empleado = new Empleado();
        empleado.setDpi(datos[0]);
        empleado.setPrimerNombre(datos[1]);
        empleado.setSegundoNombre(datos[2]);
        empleado.setPrimerApellido(datos[3]);
        empleado.setSegundoApellido(datos[4]);
        empleado.setDireccionDomiciliar(datos[5]);
        empleado.setTelefonoCasa(datos[6]);
        empleado.setTelefonoMovil(datos[7]);
        empleado.setSalarioBase(datos[8]);
        empleado.setBonificacion(datos[9]);
        return empleado;
    }
    /*Nueva ventana*/
    @FXML
    public void onOpenNewWindowClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario-view.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();
            controller.setDatabaseService(dbService);

            Stage newStage = new Stage();
            newStage.setTitle("Nueva Ventana");
            newStage.setScene(new Scene(root));

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}