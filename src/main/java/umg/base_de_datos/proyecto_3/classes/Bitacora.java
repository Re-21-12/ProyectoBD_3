package umg.base_de_datos.proyecto_3.classes;

public class Bitacora {
    private Integer id_bitacora;
    private String dpi;
    private String campo_modificado;
    private String valor_anterior;
    private String valor_nuevo;
    private String fecha;

    public Bitacora(Integer id_bitacora, String dpi, String campo_modificado, String valor_anterior, String valor_nuevo, String fecha) {
        this.id_bitacora = id_bitacora;
        this.dpi = dpi;
        this.campo_modificado = campo_modificado;
        this.valor_anterior = valor_anterior;
        this.valor_nuevo = valor_nuevo;
        this.fecha = fecha;
    }

    public Bitacora() {

    }

    public Integer getId_bitacora() {
        return id_bitacora;
    }

    public void setId_bitacora(Integer id_bitacora) {
        this.id_bitacora = id_bitacora;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getCampo_modificado() {
        return campo_modificado;
    }

    public void setCampo_modificado(String campo_modificado) {
        this.campo_modificado = campo_modificado;
    }

    public String getValor_anterior() {
        return valor_anterior;
    }

    public void setValor_anterior(String valor_anterior) {
        this.valor_anterior = valor_anterior;
    }

    public String getValor_nuevo() {
        return valor_nuevo;
    }

    public void setValor_nuevo(String valor_nuevo) {
        this.valor_nuevo = valor_nuevo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
