package umg.base_de_datos.proyecto_3.services;

import umg.base_de_datos.proyecto_3.classes.Empleado;
import umg.base_de_datos.proyecto_3.interfaces.DatabaseStrategy;

import java.sql.SQLException;

public class DatabaseService {
    private DatabaseStrategy strategy;

    public DatabaseService(DatabaseStrategy strategy) {
        this.strategy = strategy;
    }

    public void connect() {
        strategy.connect();
    }

    public void insert(Empleado empleado) throws SQLException {
        strategy.insert(empleado);
    }

    public void update(Empleado empleado) throws SQLException {
        strategy.update(empleado);
    }

    public void delete(String dpi) {
        strategy.delete(dpi);
    }

    public void setStrategy(DatabaseStrategy strategy) {
        this.strategy = strategy;
    }

}
