package umg.base_de_datos.proyecto_3.helpers;

import javafx.scene.control.Alert;
import umg.base_de_datos.proyecto_3.classes.Bitacora;
import umg.base_de_datos.proyecto_3.classes.Empleado;
import umg.base_de_datos.proyecto_3.services.DatabaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsercionesNuevas {


    public void actualizarPosgres(DatabaseService mySqlService, DatabaseService postgresService) throws SQLException {
        // Si MySQL tiene más registros, actualizar PostgreSQL
        mySqlService.selectAll().forEach(empleado -> {
            try {
                if(postgresService.selectById(empleado.getDpi()) == null)
                postgresService.insert(empleado);  // Usar el servicio de Postgres existente
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        showAlert("Éxito", "La base de datos de Postgres ha sido actualizada.", Alert.AlertType.INFORMATION);

    }

    public void actualizarMySQL(DatabaseService mySqlService, DatabaseService postgresService) throws SQLException {
        // Si PostgreSQL tiene más registros, actualizar MySQL
        postgresService.selectAll().forEach(empleado -> {
            try {
                if(mySqlService.selectById(empleado.getDpi()) == null)
                mySqlService.insert(empleado);  // Usar el servicio de MySQL existente
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        showAlert("Éxito", "La base de datos de MySQL ha sido actualizada.", Alert.AlertType.INFORMATION);
    }

    public void sincronizar(DatabaseService mySqlService, DatabaseService postgresService, String nombre) throws SQLException {
        List<Empleado> empleadosMySQl = new ArrayList<>();
        List<Bitacora> bitacorasMySQL = new ArrayList<>();
        List<Empleado> empleadosPosgress = new ArrayList<>();
        List<Bitacora> bitacorasPosgress = new ArrayList<>();

        if (nombre.equals("MySQL")) {
            bitacorasPosgress = postgresService.selectAllBitacora();
            empleadosMySQl = mySqlService.selectAll();
            //recorro las bitacoras en posgress
            for (Bitacora bitacora : bitacorasPosgress) {
                //recorro los empleados en mysql
              for(Empleado empleado: empleadosMySQl){
                  //si alguna bitacora tiene el mismo dpi que un empleado en mysql
                  if(bitacora.getDpi() == empleado.getDpi()){
                      //entonces lo actualizo en mysql
                      mySqlService.update(empleado, empleado.getDpi());
                  }
              }
            }
        }
        if (nombre.equals("Postgress")) {
            bitacorasMySQL = mySqlService.selectAllBitacora();
            empleadosPosgress = postgresService.selectAll();
            //recorro las bitacoras en mysql
            for (Bitacora bitacora : bitacorasMySQL) {
                //recorro los empleados en posgress
                for(Empleado empleado: empleadosPosgress){
                    //si alguna bitacora tiene el mismo dpi que un empleado en posgress
                    if(bitacora.getDpi() == empleado.getDpi()){
                        //entonces lo actualizo en posgress
                        postgresService.update(empleado, empleado.getDpi());
                    }
                }
            }
        }
    }

    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
