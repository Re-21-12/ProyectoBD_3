package umg.base_de_datos.proyecto_3.services;

import umg.base_de_datos.proyecto_3.interfaces.DatabaseStrategy;

public class DatabaseService {
    private DatabaseStrategy strategy;

    public DatabaseService(DatabaseStrategy strategy) {
        this.strategy = strategy;
    }

    public void connect() {
        strategy.connect();
    }

    public void insert(String data) {
        strategy.insert(data);
    }

    public void update(String data) {
        strategy.update(data);
    }

    public void delete(String id) {
        strategy.delete(id);
    }

    public void setStrategy(DatabaseStrategy strategy) {
        this.strategy = strategy;
    }

}
