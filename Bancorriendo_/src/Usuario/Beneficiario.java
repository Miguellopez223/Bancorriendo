
package Usuario;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Beneficiario {
    private String nombre;
    private int numCuenta;
    private String banco;
    private String moneda;
    private static Scanner teclado = new Scanner(System.in);

    public Beneficiario(String nombre, int numCuenta, String banco, String moneda) {
        this.nombre = nombre;
        this.numCuenta = numCuenta;
        this.banco = banco;
        this.moneda = moneda;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getNumCuenta() {
        return numCuenta;
    }

    public String getBanco() {
        return banco;
    }

    public String getMoneda() {
        return moneda;
    }



    // Métodos estáticos para manejar beneficiarios por cliente
    public static void crearBeneficiario(Cliente cliente) {
        try {
            System.out.print("Bot: Ingrese el nombre del beneficiario: ");
            String nombre = teclado.nextLine();

            System.out.print("Bot: Ingrese el banco del beneficiario: ");
            String banco = teclado.nextLine();

            System.out.print("Bot: Ingrese la moneda del beneficiario (Bs/$): ");
            String moneda = teclado.nextLine();
            while (!moneda.equals("Bs") && !moneda.equals("$")) {
                System.out.println("Error: Moneda no válida. Ingrese 'Bs' o '$'.");
                System.out.print("Bot: Ingrese la moneda del beneficiario (Bs/$): ");
                moneda = teclado.nextLine().toUpperCase();
            }

            int numCuenta = (int) (Math.random() * 9000 + 1000); // Genera un número de cuenta de 4 dígitos

            Beneficiario nuevoBeneficiario = new Beneficiario(nombre, numCuenta, banco, moneda);
            cliente.agregarBeneficiario(nuevoBeneficiario);
            System.out.println("Bot: Beneficiario creado exitosamente con número de cuenta: " + numCuenta);
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada no válida, por favor intente nuevamente.");
            teclado.nextLine(); // limpiar el buffer
        }
    }

    public static void listarBeneficiarios(Cliente cliente) {
        List<Beneficiario> beneficiarios = cliente.getBeneficiarios();
        if (beneficiarios.isEmpty()) {
            System.out.println("Bot: No tienes beneficiarios registrados.");
        } else {
            System.out.println("Lista de beneficiarios:");
            for (Beneficiario b : beneficiarios) {
                System.out.println("Nombre: " + b.getNombre() + ", Banco: " + b.getBanco() + ", Número de cuenta: " + b.getNumCuenta() + ", Moneda: " + b.getMoneda());
            }
        }
    }

    public static Beneficiario buscarBeneficiarioPorNumero(Cliente cliente, int numCuenta) {
        for (Beneficiario b : cliente.getBeneficiarios()) {
            if (b.getNumCuenta() == numCuenta) {
                return b;
            }
        }
        return null;
    }
}