package umg.base_de_datos.proyecto_3.classes;

import umg.base_de_datos.proyecto_3.interfaces.DatabaseStrategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseStrategy implements DatabaseStrategy {
    @Override
    public void connect() {
        // Lógica para conectarse a MySQL
    }

    @Override
    public void insert(String data) {
        // Lógica para insertar en MySQL
    }

    @Override
    public void update(String data) {
        // Lógica para actualizar en MySQL
    }

    @Override
    public void delete(String id) {
        // Lógica para eliminar en MySQL
    }


}

