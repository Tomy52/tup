package Model.Implementation.Cuenta;

public class Cuenta {
    private int id_cuenta;
    private int id_usuario;
    private TipoCuenta tipoCuenta;
    private double saldo;
    private String fecha_creacion;

    public Cuenta() {
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public String getTipoCuentaString() {
        return tipoCuenta.toString();
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = TipoCuenta.valueOf(tipoCuenta);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    @Override
    public String toString() {
        return "Id cuenta: " + id_cuenta + "\n" +
                "Id usuario: " + id_usuario + "\n" +
                "Tipo: " + tipoCuenta + "\n" +
                "Saldo: " + saldo + "\n" +
                "Fecha de creacion: " + fecha_creacion;
    }
}
