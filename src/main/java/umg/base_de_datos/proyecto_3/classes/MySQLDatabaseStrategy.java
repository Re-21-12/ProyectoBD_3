package umg.base_de_datos.proyecto_3.classes;

import umg.base_de_datos.proyecto_3.interfaces.DatabaseStrategy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabaseStrategy implements DatabaseStrategy {
    private static final String URL = "jdbc:mysql://localhost:3306/Proyecto3";
    private static final String USER = "VictorAdmin";
    private static final String PASSWORD = "Alfredo+123";
    private Connection connection;

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to MySQL established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Empleado empleado) throws SQLException {
        String query = "INSERT INTO empleados (dpi, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido,direccion_domiciliar, telefono_de_casa, telefono_movil, salario_base, bonificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, empleado.getDpi());
        preparedStatement.setString(2, empleado.getPrimerNombre());
        preparedStatement.setString(3, empleado.getSegundoNombre());
        preparedStatement.setString(4, empleado.getPrimerApellido());
        preparedStatement.setString(5, empleado.getSegundoApellido());
        preparedStatement.setString(6, empleado.getDireccionDomiciliar());
        preparedStatement.setString(7, empleado.getTelefonoCasa());
        preparedStatement.setString(8, empleado.getTelefonoMovil());
        preparedStatement.setDouble(9, Double.parseDouble(empleado.getSalarioBase()));
        preparedStatement.setDouble(10, Double.parseDouble(empleado.getBonificacion()));
        preparedStatement.executeUpdate();
    }

    //Todo: Revisar si esta bien el update
    @Override
    public void update(Empleado empleado, String dpi) {
        String query = "UPDATE empleados SET primer_nombre = ?, segundo_nombre = ?, primer_apellido = ?,segundo_apellido = ?, direccion_domiciliar = ?, telefono_de_casa = ?, telefono_movil = ?,salario_base = ?, bonificacion = ? WHERE dpi = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, empleado.getPrimerNombre());
            preparedStatement.setString(2, empleado.getSegundoNombre());
            preparedStatement.setString(3, empleado.getPrimerApellido());
            preparedStatement.setString(4, empleado.getSegundoApellido());
            preparedStatement.setString(5, empleado.getDireccionDomiciliar());
            preparedStatement.setString(6, empleado.getTelefonoCasa());
            preparedStatement.setString(7, empleado.getTelefonoMovil());
            preparedStatement.setDouble(8, Double.parseDouble(empleado.getSalarioBase()));
            preparedStatement.setDouble(9, Double.parseDouble(empleado.getBonificacion()));
            preparedStatement.setString(10, empleado.getDpi());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void limpiarBitacora() {
        String query = "DELETE FROM bitacora";
        executeUpdate(query);
    }

    @Override
    public List<Empleado> selectAll() {
        String query = "SELECT * FROM empleados";
        List<Empleado> empleados = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setDpi(resultSet.getString("dpi"));
                empleado.setPrimerNombre(resultSet.getString("primer_nombre"));
                empleado.setSegundoNombre(resultSet.getString("segundo_nombre"));
                empleado.setPrimerApellido(resultSet.getString("primer_apellido"));
                empleado.setSegundoApellido(resultSet.getString("segundo_apellido"));
                empleado.setDireccionDomiciliar(resultSet.getString("direccion_domiciliar"));
                empleado.setTelefonoCasa(resultSet.getString("telefono_de_casa"));
                empleado.setTelefonoMovil(resultSet.getString("telefono_movil"));
                empleado.setSalarioBase(String.valueOf(resultSet.getDouble("salario_base")));
                empleado.setBonificacion(String.valueOf(resultSet.getDouble("bonificacion")));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM empleados";
        int count = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Bitacora> selectAllBitacora() {
        String query = "SELECT * FROM bitacora";
        List<Bitacora> bitacoras = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bitacora bitacora = new Bitacora();
                bitacora.setId_bitacora(resultSet.getInt("id_bitacora"));
                bitacora.setDpi(resultSet.getString("dpi"));
                bitacora.setCampo_modificado(resultSet.getString("campo_modificado"));
                bitacora.setValor_anterior(resultSet.getString("valor_anterior"));
                bitacora.setValor_nuevo(resultSet.getString("valor_nuevo"));
                bitacora.setFecha(resultSet.getString("fecha"));
                bitacoras.add(bitacora);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bitacoras;
    }

    @Override
    public void delete(String dpi) {
        String query = "DELETE FROM empleados WHERE dpi = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dpi);
            preparedStatement.executeUpdate();  // Usar executeUpdate para eliminar registros
            System.out.println("Empleado con DPI " + dpi + " ha sido eliminado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Empleado selectById(String dpi) {
        Empleado empleado = new Empleado();
        String query = "SELECT * FROM empleados WHERE dpi = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dpi);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                empleado.setDpi(resultSet.getString("dpi"));
                empleado.setPrimerNombre(resultSet.getString("primer_nombre"));
                empleado.setSegundoNombre(resultSet.getString("segundo_nombre"));
                empleado.setPrimerApellido(resultSet.getString("primer_apellido"));
                empleado.setSegundoApellido(resultSet.getString("segundo_apellido"));
                empleado.setDireccionDomiciliar(resultSet.getString("direccion_domiciliar"));
                empleado.setTelefonoCasa(resultSet.getString("telefono_de_casa"));
                empleado.setTelefonoMovil(resultSet.getString("telefono_movil"));
                empleado.setSalarioBase(String.valueOf(resultSet.getDouble("salario_base")));
                empleado.setBonificacion(String.valueOf(resultSet.getDouble("bonificacion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleado;
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