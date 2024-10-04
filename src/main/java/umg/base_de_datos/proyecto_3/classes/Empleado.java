package umg.base_de_datos.proyecto_3.classes;

public class Empleado {
    private String dpi;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String direccionDomiciliar;
    private String telefonoCasa;
    private String telefonoMovil;
    private String salarioBase;
    private String bonificacion;

    public Empleado() {
    }
    public Empleado(String dpi, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String direccionDomiciliar, String telefonoCasa, String telefonoMovil, String salarioBase, String bonificacion) {
        this.dpi = dpi;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccionDomiciliar = direccionDomiciliar;
        this.telefonoCasa = telefonoCasa;
        this.telefonoMovil = telefonoMovil;
        this.salarioBase = salarioBase;
        this.bonificacion = bonificacion;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDireccionDomiciliar() {
        return direccionDomiciliar;
    }

    public void setDireccionDomiciliar(String direccionDomiciliar) {
        this.direccionDomiciliar = direccionDomiciliar;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(String salarioBase) {
        this.salarioBase = salarioBase;
    }

    public String getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(String bonificacion) {
        this.bonificacion = bonificacion;
    }
}
