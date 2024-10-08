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
import javafx.scene.control.Alert.AlertType;

import umg.base_de_datos.proyecto_3.helpers.InsercionesNuevas;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<String> MySQL;
    @FXML
    private ListView<String> Postgress;
    @FXML
    private TextField dpiInputField;

    private DatabaseService dbService;
    private InsercionesNuevas insercionesNuevas = new InsercionesNuevas();
    private String nombreDatabase = "";

    @FXML
    public void onMySQLButtonClick() {
        nombreDatabase = "MySQL";
        dbService = new DatabaseService(new MySQLDatabaseStrategy());
        dbService.connect();
    }

    @FXML
    public void onPostgresButtonClick() {
        nombreDatabase = "Postgress";
        dbService = new DatabaseService(new PostgresDatabaseStrategy());
        dbService.connect();
    }


    @FXML
    public void onDeleteButtonClick() throws SQLException {
        if (dpiInputField.getText().isEmpty())
            insercionesNuevas.showAlert("Error", "Debe ingresar un DPI", AlertType.ERROR);

        if (dbService.selectById(dpiInputField.getText()) == null)
            insercionesNuevas.showAlert("Error", "No se encuentra este empleado.", AlertType.ERROR);

        dbService.delete(dpiInputField.getText());
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        String dpi = dpiInputField.getText();

        if (dpi == null || dpi.isEmpty()) {
            // Muestra una alerta si no se ingresó DPI
            insercionesNuevas.showAlert("Error", "Debe ignresar un DPI.", AlertType.ERROR);
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


    @FXML
    public void onSynchronizeButtonClick() throws SQLException {
        // Crear instancias de servicio para ambas bases de datos
        DatabaseService mySqlService = new DatabaseService(new MySQLDatabaseStrategy());
        DatabaseService postgresService = new DatabaseService(new PostgresDatabaseStrategy());

        // Conectar a ambas bases de datos
        mySqlService.connect();
        postgresService.connect();

        // Contar registros en ambas bases de datos
        int mySqlCount = mySqlService.count();
        int postgresCount = postgresService.count();

        // Comparar conteos y actuar en consecuencia
        if (mySqlCount == postgresCount)
            insercionesNuevas.showAlert("Éxito", "Las bases de datos están sincronizadas.", AlertType.INFORMATION);
        //actualizamos postgress por que es el que tiene menos contenido
        if (mySqlCount > postgresCount) {
            System.out.println("Postgress tiene menos contenido");
            nombreDatabase = "Postgress";
            insercionesNuevas.actualizarPosgres(mySqlService, postgresService);
            insercionesNuevas.sincronizar(mySqlService, postgresService, nombreDatabase);

        }
        //actualizamos Mysql por que es el que tiene menos contenido

        if (mySqlCount < postgresCount){
            nombreDatabase = "MySQL";
            insercionesNuevas.actualizarMySQL(mySqlService, postgresService);
            insercionesNuevas.sincronizar(mySqlService, postgresService, nombreDatabase);
        }

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

    public void onShow(ActionEvent actionEvent) throws SQLException {
        MySQL.getItems().clear();
        Postgress.getItems().clear();

        if (nombreDatabase.equals("MySQL"))
            dbService.selectAll().forEach(empleado -> MySQL.getItems().add(empleado.toString()));
        if (nombreDatabase.equals("Postgress"))
            dbService.selectAll().forEach(empleado -> Postgress.getItems().add(empleado.toString()));


    }
}