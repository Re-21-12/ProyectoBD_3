package umg.base_de_datos.proyecto_3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import umg.base_de_datos.proyecto_3.classes.Empleado;
import umg.base_de_datos.proyecto_3.services.DatabaseService;
import umg.base_de_datos.proyecto_3.helpers.InsercionesNuevas;

import java.sql.SQLException;

public class FormularioController {
    @FXML
    private TextField dpiField, primerNombreField, segundoNombreField, primerApellidoField, segundoApellidoField,
            direccionField, telefonoCasaField, telefonoMovilField, salarioBaseField, bonificacionField;
    @FXML
    private Button insertButton, updateButton;

    private DatabaseService dbService; // Instancia de DatabaseService
    private Empleado empleado;
    private InsercionesNuevas helper = new InsercionesNuevas();
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

        if(empleado.getDpi() == null || empleado.getDpi().isEmpty()){
            helper.showAlert("Error", "El DPI no puede estar vacío.", AlertType.ERROR);
            return;
        }
        // Insertar el empleado en la base de datos
        try {
            dbService.insert(empleado); // Aquí utilizas la instancia de dbService
            System.out.println("Empleado insertado: " + empleado);
            helper.showAlert("Éxito", "Empleado insertado correctamente.", AlertType.INFORMATION);
        } catch (SQLException | NumberFormatException e) {
            helper.showAlert("Error", "No se pudo insertar el empleado. " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void setEmpleado(Empleado empleado) throws SQLException {
        this.empleado = empleado;
        loadEmpleadoData(empleado.getDpi());
    }

    @FXML
    public void onUpdateButtonClick() throws SQLException {
        // Obtener los valores de los campos de texto y crear un objeto Empleado
        empleado.setDpi(dpiField.getText());
        Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setDpi(dpiField.getText());
        empleadoActualizado.setPrimerNombre(primerNombreField.getText());
        empleadoActualizado.setSegundoNombre(segundoNombreField.getText());
        empleadoActualizado.setPrimerApellido(primerApellidoField.getText());
        empleadoActualizado.setSegundoApellido(segundoApellidoField.getText());
        empleadoActualizado.setDireccionDomiciliar(direccionField.getText());
        empleadoActualizado.setTelefonoCasa(telefonoCasaField.getText());
        empleadoActualizado.setTelefonoMovil(telefonoMovilField.getText());
        empleadoActualizado.setSalarioBase(salarioBaseField.getText());
        empleadoActualizado.setBonificacion(bonificacionField.getText());
        // Insertar el empleado en la base de datos
        try {
            dbService.update(empleadoActualizado, empleado.getDpi()); // Aquí utilizas la instancia de dbService
            System.out.println("Empleado actualizado: " + empleadoActualizado);
            helper.showAlert("Éxito", "Empleado actualizado correctamente.", AlertType.INFORMATION);
        } catch (SQLException | NumberFormatException e) {
            helper.showAlert("Error", "No se pudo actualizar el empleado. " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteButtonClick() {
        String selectedItem = dpiField.getText(); // Asume que seleccionas el DPI del empleado para eliminar
        if (selectedItem != null) {
            dbService.delete(selectedItem);
            System.out.println("Empleado eliminado: " + selectedItem);
            helper.showAlert("Éxito", "Empleado eliminado correctamente.", AlertType.INFORMATION);
        } else {
            helper.showAlert("Advertencia", "Seleccione un empleado para eliminar.", AlertType.WARNING);
        }
    }

    public void loadEmpleadoData(String dpi) throws SQLException {
        Empleado empleado = dbService.selectById(dpi);

        if (empleado != null) {
            // Rellenar los campos del formulario con los datos del empleado
            dpiField.setText(empleado.getDpi());
            primerNombreField.setText(empleado.getPrimerNombre());
            segundoNombreField.setText(empleado.getSegundoNombre());
            primerApellidoField.setText(empleado.getPrimerApellido());
            segundoApellidoField.setText(empleado.getSegundoApellido());
            direccionField.setText(empleado.getDireccionDomiciliar());
            telefonoCasaField.setText(empleado.getTelefonoCasa());
            telefonoMovilField.setText(empleado.getTelefonoMovil());
            salarioBaseField.setText(String.valueOf(empleado.getSalarioBase()));
            bonificacionField.setText(String.valueOf(empleado.getBonificacion()));

            dpiField.setDisable(true);
            insertButton.setDisable(true);
        } else {
            // Mostrar alerta si no se encuentra el empleado
            helper.showAlert("Error", "No se encontró un empleado con el DPI especificado.", AlertType.ERROR);
        }
    }

    public void loadEmpleadoData() throws SQLException {
        updateButton.setDisable(true);
    }

    @FXML
    public void onExitButtonClick() {
        // Obtener el stage actual y cerrarlo
        Stage stage = (Stage) dpiField.getScene().getWindow();
        stage.close();
    }


}
