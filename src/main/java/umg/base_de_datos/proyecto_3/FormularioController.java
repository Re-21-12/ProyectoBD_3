package umg.base_de_datos.proyecto_3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umg.base_de_datos.proyecto_3.classes.Empleado;
import umg.base_de_datos.proyecto_3.services.DatabaseService;

import java.sql.SQLException;

public class FormularioController {
    @FXML
    private TextField dpiField, primerNombreField, segundoNombreField, primerApellidoField, segundoApellidoField,
            direccionField, telefonoCasaField, telefonoMovilField, salarioBaseField, bonificacionField;

    private DatabaseService dbService; // Instancia de DatabaseService

    public void setDatabaseService(DatabaseService dbService) {
        this.dbService = dbService; // Establece la instancia recibida
    }

    @FXML
    public void onInsertButtonClick() {
        // Obtener los valores de los campos de texto y crear un objeto Empleado
        Empleado empleado = new Empleado();
        empleado.setDpi(dpiField.getText());
        empleado.setPrimerNombre(primerNombreField.getText());
        empleado.setSegundoNombre(segundoNombreField.getText());
        empleado.setPrimerApellido(primerApellidoField.getText());
        empleado.setSegundoApellido(segundoApellidoField.getText());
        empleado.setDireccionDomiciliar(direccionField.getText());
        empleado.setTelefonoCasa(telefonoCasaField.getText());
        empleado.setTelefonoMovil(telefonoMovilField.getText());
        empleado.setSalarioBase(salarioBaseField.getText());
        empleado.setBonificacion(bonificacionField.getText());

        // Insertar el empleado en la base de datos
        try {
            dbService.insert(empleado); // Aquí utilizas la instancia de dbService
            System.out.println("Empleado insertado: " + empleado);
            showAlert("Éxito", "Empleado insertado correctamente.", AlertType.INFORMATION);
        } catch (SQLException | NumberFormatException e) {
            showAlert("Error", "No se pudo insertar el empleado. " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteButtonClick() {
        String selectedItem = dpiField.getText(); // Asume que seleccionas el DPI del empleado para eliminar
        if (selectedItem != null) {
            dbService.delete(selectedItem);
            System.out.println("Empleado eliminado: " + selectedItem);
            showAlert("Éxito", "Empleado eliminado correctamente.", AlertType.INFORMATION);
        } else {
            showAlert("Advertencia", "Seleccione un empleado para eliminar.", AlertType.WARNING);
        }
    }

    @FXML
    public void onUpdateButtonClick() {
        String data = dpiField.getText(); // Asume que el DPI es el identificador para actualizar
        Empleado empleado = new Empleado(); // Crea el objeto empleado
        empleado.setDpi(data); // Establece el DPI
        empleado.setPrimerNombre(primerNombreField.getText());
        empleado.setSegundoNombre(segundoNombreField.getText());
        empleado.setPrimerApellido(primerApellidoField.getText());
        empleado.setSegundoApellido(segundoApellidoField.getText());
        empleado.setDireccionDomiciliar(direccionField.getText());
        empleado.setTelefonoCasa(telefonoCasaField.getText());
        empleado.setTelefonoMovil(telefonoMovilField.getText());
        empleado.setSalarioBase(salarioBaseField.getText());
        empleado.setBonificacion(bonificacionField.getText());

        try {
            dbService.update(empleado);
            showAlert("Éxito", "Empleado actualizado correctamente.", AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo actualizar el empleado. " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onExitButtonClick() {
        // Obtener el stage actual y cerrarlo
        Stage stage = (Stage) dpiField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
