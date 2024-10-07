package umg.base_de_datos.proyecto_3.interfaces;

import umg.base_de_datos.proyecto_3.classes.Bitacora;
import umg.base_de_datos.proyecto_3.classes.Empleado;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/*Ambas bases de datos necesitan obligatoriamente realizar estas acciones*/
public interface DatabaseStrategy {
    void connect();

    void insert(Empleado empleado) throws SQLException;

    void update(Empleado empleado, String dpi) throws SQLException;

    void delete(String dpi);

    List<Empleado> selectAll() throws SQLException;
    List<Bitacora> selectAllBitacora() throws SQLException;

    Empleado selectById(String dpi) throws SQLException;

    int count() throws SQLException;
}
