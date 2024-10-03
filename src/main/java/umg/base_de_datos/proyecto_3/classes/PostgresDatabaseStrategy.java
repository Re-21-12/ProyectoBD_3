package umg.base_de_datos.proyecto_3.classes;


import umg.base_de_datos.proyecto_3.interfaces.DatabaseStrategy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresDatabaseStrategy implements DatabaseStrategy {
    @Override
    public void connect() {
        // Lógica para conectarse a PostgreSQL
    }

    @Override
    public void insert(String data) {
        // Lógica para insertar en PostgreSQL
    }

    @Override
    public void update(String data) {
        // Lógica para actualizar en PostgreSQL
    }

    @Override
    public void delete(String id) {
        // Lógica para eliminar en PostgreSQL
    }


}

