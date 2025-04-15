package org.tomy52.tp3_conspring.Model.Implementation.Cuenta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Cuenta {
    private int id_cuenta;
    private int id_usuario;
    private TipoCuenta tipoCuenta;
    private double saldo;
    private String fecha_creacion;

    public String getTipoCuentaString() {
        return tipoCuenta.toString();
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = TipoCuenta.valueOf(tipoCuenta);
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
