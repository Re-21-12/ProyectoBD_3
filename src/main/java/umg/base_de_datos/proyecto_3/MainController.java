package umg.base_de_datos.proyecto_3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umg.base_de_datos.proyecto_3.classes.Empleado;
import umg.base_de_datos.proyecto_3.classes.MySQLDatabaseStrategy;
import umg.base_de_datos.proyecto_3.classes.PostgresDatabaseStrategy;
import umg.base_de_datos.proyecto_3.services.DatabaseService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField dpiInputField;

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
    public void onDeleteButtonClick() throws SQLException {
        if (dpiInputField.getText().isEmpty())
            showAlert("Error", "Debe ingresar un DPI", AlertType.ERROR);

        if (dbService.selectById(dpiInputField.getText()) == null)
            showAlert("Error", "No se encuentra este empleado.", AlertType.ERROR);

        dbService.delete(dpiInputField.getText());
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        String dpi = dpiInputField.getText();

        if (dpi == null || dpi.isEmpty()) {
            // Muestra una alerta si no se ingresó DPI
            showAlert("Error", "Debe ignresar un DPI.", AlertType.ERROR);
            return;
        }

        // Llamar al método que abre el formulario de edición
        openFormularioEdicion(dpi);
    }

    @FXML
    // Método para abrir el formulario de edición
    private void openFormularioEdicion(String dpi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario-view.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            FormularioController controller = loader.getController();
            controller.setDatabaseService(dbService);
            Empleado empleado = dbService.selectById(dpi);
            controller.setEmpleado(empleado);

            // Crear una nueva ventana para el formulario
            Stage stage = new Stage();
            stage.setTitle("Editar Empleado");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onSynchronizeButtonClick() {
        // Lógica para sincronizar tablas entre las dos bases de datos
    }

    /*Nueva ventana*/
    @FXML
    public void onOpenNewWindowClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario-view.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();
            controller.setDatabaseService(dbService);
            controller.loadEmpleadoData();
            Stage newStage = new Stage();
            newStage.setTitle("Insertar Ventana");
            newStage.setScene(new Scene(root));

            newStage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}