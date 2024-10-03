package umg.base_de_datos.proyecto_3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import umg.base_de_datos.proyecto_3.classes.MySQLDatabaseStrategy;
import umg.base_de_datos.proyecto_3.classes.PostgresDatabaseStrategy;
import umg.base_de_datos.proyecto_3.services.DatabaseService;

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
        dbService.insert(data);
        listView.getItems().add(data);
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
            dbService.update(data);
            listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), data);
        }
    }

    @FXML
    public void onSynchronizeButtonClick() {
        // Lógica para sincronizar tablas entre las dos bases de datos
    }
}