package Pago;

import Usuario.Cliente;
import Usuario.Cuenta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Servicios {

    private static Scanner teclado = new Scanner(System.in);

    public static int pagarServicio(Cuenta cuenta, String nombreServicio, int[] medidores, Cliente cliente) {
        // Verificar que la cuenta esté en bolivianos
        if (!cuenta.getMoneda().equals("Bs")) {
            System.out.println("Error: El servicio solo puede ser pagado con una cuenta en bolivianos.");
            return -1;
        }

        // Verificar si todos los medidores están en 0
        boolean todosEnCero = true;
        for (int medidor : medidores) {
            if (medidor > 0) {
                todosEnCero = false;
                break;
            }
        }

        if (todosEnCero) {
            System.out.println("Bot: Todos los medidores de " + nombreServicio + " ya han sido pagados. No hay nada más que pagar.");
            return -1; // Retornar al menú del cliente
        }

        // Mostrar los medidores disponibles
        System.out.println("Bot: Medidores disponibles para " + nombreServicio + ":");
        for (int i = 0; i < medidores.length; i++) {
            System.out.println((i + 1) + ". Monto: " + medidores[i] + " Bs");
        }

        // Seleccionar el medidor a pagar
        int seleccionMedidor = 0;
        while (true) {
            try {
                System.out.print("Bot: Seleccione el medidor que desea pagar: ");
                seleccionMedidor = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer

                if (seleccionMedidor < 1 || seleccionMedidor > medidores.length) {
                    System.out.println("Error: Selección no válida. Por favor, seleccione un medidor válido.");
                } else if (medidores[seleccionMedidor - 1] == 0) {
                    System.out.println("Error: El medidor seleccionado ya ha sido pagado. Seleccione otro medidor.");
                } else {
                    break;  // Salir del ciclo si la selección es válida
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor intente nuevamente.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Obtener el monto pendiente del medidor seleccionado
        int montoPendiente = medidores[seleccionMedidor - 1];

        // Verificar si la cuenta tiene suficiente saldo
        if (cuenta.getSaldo() >= montoPendiente) {
            cuenta.setSaldo(cuenta.getSaldo() - montoPendiente);

            // Establecer el medidor en cero después del pago
            medidores[seleccionMedidor - 1] = 0;

            // Registrar la transacción
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            String transaccion = "Pago de " + nombreServicio + " de " + montoPendiente + " Bs el " + fecha;
            cuenta.agregarTransaccion(transaccion);

            System.out.println("Bot: El pago de " + nombreServicio + " por " + montoPendiente + " Bs se ha realizado con éxito.");
        } else {
            System.out.println("Error: Saldo insuficiente para pagar el servicio.");
        }

        return seleccionMedidor - 1;  // Devolver el índice del medidor pagado
    }

    public static void pagarLuz(Cliente cliente, Cuenta cuenta) {
        // Realizar el pago del servicio de luz y obtener el índice del medidor pagado
        int indiceMedidor = pagarServicio(cuenta, "Luz", cliente.getMedidoresLuz(), cliente);
        // Resetear el medidor si es necesario
        cliente.resetMedidorLuz(indiceMedidor);
    }

    public static void pagarAgua(Cliente cliente, Cuenta cuenta) {
        // Realizar el pago del servicio de agua y obtener el índice del medidor pagado
        int indiceMedidor = pagarServicio(cuenta, "Agua", cliente.getMedidoresAgua(), cliente);
        // Resetear el medidor si es necesario
        cliente.resetMedidorAgua(indiceMedidor);
    }

    public static void pagarTelecomunicaciones(Cliente cliente, Cuenta cuenta) {
        // Realizar el pago del servicio de telecomunicaciones y obtener el índice del medidor pagado
        int indiceMedidor = pagarServicio(cuenta, "Telecomunicaciones", cliente.getMedidoresTelecomunicaciones(), cliente);
        // Resetear el medidor si es necesario
        cliente.resetMedidorTelecomunicaciones(indiceMedidor);
    }

    public static void pagarColegiatura(Cliente cliente, Cuenta cuenta) {
        // Verificar que la cuenta esté en bolivianos
        if (!cuenta.getMoneda().equals("Bs")) {
            System.out.println("Error: La colegiatura solo puede ser pagada con una cuenta en bolivianos.");
            return;
        }

        double montoPendiente = cliente.getColegiaturaPendiente();

        if (cuenta.getSaldo() >= montoPendiente) {
            cuenta.setSaldo(cuenta.getSaldo() - montoPendiente);
            cliente.resetColegiatura(); // Resetear la colegiatura después del pago

            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            String transaccion = "Pago de Colegiatura de " + montoPendiente + " Bs el " + fecha;
            cuenta.agregarTransaccion(transaccion);

            System.out.println("Bot: El pago de Colegiatura por " + montoPendiente + " Bs se ha realizado con éxito.");
        } else {
            System.out.println("Error: Saldo insuficiente para pagar la colegiatura.");
        }
    }
}