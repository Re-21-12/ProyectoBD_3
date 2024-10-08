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

    private DatabaseService mysqlService;
    private DatabaseService postgresService;
    private DatabaseService dbService;  // Referencia dinámica para cambiar entre instancias
    private InsercionesNuevas insercionesNuevas = new InsercionesNuevas();
    private String nombreDatabase = "";

    // Inicializamos las conexiones solo una vez
    public MainController() {
        mysqlService = new DatabaseService(new MySQLDatabaseStrategy());
        postgresService = new DatabaseService(new PostgresDatabaseStrategy());
    }

    // Método para cambiar la referencia de base de datos actual
    private void switchDatabaseService(String tipoDatabase) {
        switch (tipoDatabase) {
            case "MySQL":
                dbService = mysqlService;  // Apuntamos a la instancia de MySQL
                nombreDatabase = "MySQL";
                break;
            case "Postgress":
                dbService = postgresService;  // Apuntamos a la instancia de Postgres
                nombreDatabase = "Postgress";
                break;
        }

        // Aseguramos que esté conectada
        dbService.connect();
    }

    @FXML
    public void onMySQLButtonClick() {
        switchDatabaseService("MySQL");
    }

    @FXML
    public void onPostgresButtonClick() {
        switchDatabaseService("Postgress");
    }

    @FXML
    public void onDeleteButtonClick() throws SQLException {
        if (dpiInputField.getText().isEmpty()) {
            insercionesNuevas.showAlert("Error", "Debe ingresar un DPI", AlertType.ERROR);
            return;
        }

        if (dbService.selectById(dpiInputField.getText()) == null) {
            insercionesNuevas.showAlert("Error", "No se encuentra este empleado.", AlertType.ERROR);
            return;
        }

        dbService.delete(dpiInputField.getText());
        insercionesNuevas.showAlert("Éxito", "Empleado eliminado correctamente.", AlertType.INFORMATION);
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        String dpi = dpiInputField.getText();

        if (dpi == null || dpi.isEmpty()) {
            insercionesNuevas.showAlert("Error", "Debe ingresar un DPI.", AlertType.ERROR);
            return;
        }

        openFormularioEdicion(dpi);
    }

    private void openFormularioEdicion(String dpi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Formulario-view.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();
            controller.setDatabaseService(dbService);
            Empleado empleado = dbService.selectById(dpi);
            controller.setEmpleado(empleado);

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
        int mySqlCount = mysqlService.count();
        int postgresCount = postgresService.count();

        if (mySqlCount == postgresCount) {
            insercionesNuevas.showAlert("Éxito", "Las bases de datos están sincronizadas.", AlertType.INFORMATION);
        } else if (mySqlCount > postgresCount) {
            nombreDatabase = "Postgress";
            insercionesNuevas.actualizarPosgres(mysqlService, postgresService);
            insercionesNuevas.sincronizar(mysqlService, postgresService, nombreDatabase);
        } else {
            nombreDatabase = "MySQL";
            insercionesNuevas.actualizarMySQL(mysqlService, postgresService);
            insercionesNuevas.sincronizar(mysqlService, postgresService, nombreDatabase);
        }
    }

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

    @FXML
    public void onShow(ActionEvent actionEvent) throws SQLException {
        MySQL.getItems().clear();
        Postgress.getItems().clear();

        dbService.selectAll().forEach(empleado -> {
            if (nombreDatabase.equals("MySQL")) {
                MySQL.getItems().add(empleado.toString());
            } else if (nombreDatabase.equals("Postgress")) {
                Postgress.getItems().add(empleado.toString());
            }
        });
    }
}
