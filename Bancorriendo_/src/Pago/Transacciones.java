package Pago;

import Usuario.Beneficiario;
import Usuario.Cuenta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transacciones {
    private static Scanner teclado = new Scanner(System.in);

    public static void abonar(Cuenta cuenta, double monto) {
        if (monto > 0) {
            cuenta.setSaldo(cuenta.getSaldo() + monto);

            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            String transaccion = "Abono de " + monto + " " + cuenta.getMoneda() + " el " + fecha;
            cuenta.agregarTransaccion(transaccion);

            System.out.println("Bot: Se han abonado " + monto + " " + cuenta.getMoneda() + " a la cuenta " + cuenta.getNumCuenta());
        } else {
            System.out.println("Error: El monto a abonar debe ser mayor a 0.");
        }
    }

    public static void debitar(Cuenta cuenta, double monto) {
        if (monto > 0 && cuenta.getSaldo() >= monto) {
            cuenta.setSaldo(cuenta.getSaldo() - monto);

            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            String transaccion = "Debito de " + monto + " " + cuenta.getMoneda() + " el " + fecha;
            cuenta.agregarTransaccion(transaccion);

            System.out.println("Bot: Se han debitado " + monto + " " + cuenta.getMoneda() + " de la cuenta " + cuenta.getNumCuenta());
        } else if (monto <= 0) {
            System.out.println("Error: El monto a debitar debe ser mayor a 0.");
        } else {
            System.out.println("Error: Saldo insuficiente.");
        }
    }

    public static void transferir(Cuenta origen, Beneficiario beneficiario, double monto) {
        try {
            // Validación de la moneda
            if (!origen.getMoneda().equals(beneficiario.getMoneda())) {
                System.out.println("Error: No se puede realizar la transferencia. Las cuentas deben tener la misma moneda.");
                return;
            }

            if (monto > 0 && origen.getSaldo() >= monto) {
                origen.setSaldo(origen.getSaldo() - monto);

                String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                String transaccion = "Transferencia de " + monto + " " + origen.getMoneda() + " a " + beneficiario.getNombre() + " (Cuenta: " + beneficiario.getNumCuenta() + ") el " + fecha;
                origen.agregarTransaccion(transaccion);

                // Aquí podrías implementar la lógica para acreditar el monto al beneficiario en otro banco si es necesario.
                // Por simplicidad, asumiremos que es una transferencia externa y solo se debita de la cuenta de origen.

                System.out.println("Bot: Se han transferido " + monto + " " + origen.getMoneda() + " a " + beneficiario.getNombre() + " (Cuenta: " + beneficiario.getNumCuenta() + ")");
            } else if (monto <= 0) {
                System.out.println("Error: El monto a transferir debe ser mayor a 0.");
            } else {
                System.out.println("Error: Saldo insuficiente.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ocurrió un problema al realizar la transferencia. Por favor, intente nuevamente.");
        }
    }
}