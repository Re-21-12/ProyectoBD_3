package umg.base_de_datos.proyecto_3.classes;

import umg.base_de_datos.proyecto_3.interfaces.DatabaseStrategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresDatabaseStrategy implements DatabaseStrategy {
    private static final String URL = "jdbc:postgresql://localhost:5432/Proyecto3";
    private static final String USER = "VictorAdmin";
    private static final String PASSWORD = "Alfredo+123";
    private Connection connection;

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to PostgreSQL established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String data) {
        String[] datos = data.split(",");
        String id = datos[0];
        String nombre = datos[1];

        String query = "INSERT INTO tabla (id, nombre) VALUES ('" + id + "', '" + nombre + "')";
        executeUpdate(query);
    }

    @Override
    public void update(String data) {
        String[] datos = data.split(",");
        String id = datos[0];
        String nombre = datos[1];
        String query = "UPDATE tabla SET nombre = '" + nombre + "' WHERE id = '" + id + "'";
        executeUpdate(query);
    }

    @Override
    public void delete(String id) {
        String query = "DELETE FROM tabla WHERE id = " + id;
        executeUpdate(query);
    }

    private void executeUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Query executed: " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}