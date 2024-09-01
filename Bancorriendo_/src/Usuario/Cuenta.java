package Usuario;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private int numCuenta;
    private double saldo;
    private String moneda;
    private List<String> transacciones;

    public Cuenta(int numCuenta, double saldo, String moneda) {
        this.numCuenta = numCuenta;
        this.saldo = saldo;
        this.moneda = moneda;
        this.transacciones = new ArrayList<>();
    }

    public void agregarTransaccion(String descripcion) {
        transacciones.add(descripcion);
    }

    public void mostrarTransacciones() {
        if (transacciones.isEmpty()) {
            System.out.println("Bot: No hay transacciones recientes.");
        } else {
            System.out.println("Transacciones recientes para la cuenta " + numCuenta + ":");
            for (String transaccion : transacciones) {
                System.out.println(transaccion);
            }
        }
    }

    public int getNumCuenta() {
        return numCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Número de Cuenta: " + numCuenta + ", Saldo: " + saldo + " " + moneda;
    }
}
