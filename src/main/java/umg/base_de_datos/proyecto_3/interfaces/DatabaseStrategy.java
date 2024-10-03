package umg.base_de_datos.proyecto_3.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/*Ambas bases de datos necesitan obligatoriamente realizar estas acciones*/
public interface DatabaseStrategy {
    void connect();

    void insert(String data);

    void update(String data);

    void delete(String id);

}

