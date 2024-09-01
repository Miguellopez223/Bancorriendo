package Usuario;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cliente {

    private static List<Cliente> clientes = new ArrayList<>();
    private String nombre;
    private int pin;
    private List<Cuenta> cuentas = new ArrayList<>();
    private List<Beneficiario> beneficiarios = new ArrayList<>();

    // Medidores específicos para cada cliente
    private int[] medidoresAgua = {150, 100};
    private int[] medidoresLuz = {400, 350};
    private int[] medidoresTelecomunicaciones = {300, 200};
    private double colegiaturaPendiente = 7000; // Valor inicial para colegiatura

    private static Scanner teclado = new Scanner(System.in);

    public Cliente(String nombre, int pin) {
        this.nombre = nombre;
        this.pin = pin;
    }

    // Getters para los medidores y colegiatura
    public int[] getMedidoresAgua() {
        return medidoresAgua;
    }

    public int[] getMedidoresLuz() {
        return medidoresLuz;
    }

    public int[] getMedidoresTelecomunicaciones() {
        return medidoresTelecomunicaciones;
    }

    public double getColegiaturaPendiente() {
        return colegiaturaPendiente;
    }

    public void setColegiaturaPendiente(double colegiaturaPendiente) {
        this.colegiaturaPendiente = colegiaturaPendiente;
    }

    // Métodos para resetear los medidores y colegiatura
    public void resetMedidorLuz(int indice) {
        if (indice >= 0 && indice < medidoresLuz.length) {
            medidoresLuz[indice] = 0;
        } else {
            System.out.println("Error: Índice de medidor de luz no válido.");
        }
    }

    public void resetMedidorAgua(int indice) {
        if (indice >= 0 && indice < medidoresAgua.length) {
            medidoresAgua[indice] = 0;
        } else {
            System.out.println("Error: Índice de medidor de agua no válido.");
        }
    }

    public void resetMedidorTelecomunicaciones(int indice) {
        if (indice >= 0 && indice < medidoresTelecomunicaciones.length) {
            medidoresTelecomunicaciones[indice] = 0;
        } else {
            System.out.println("Error: Índice de medidor de telecomunicaciones no válido.");
        }
    }

    public void resetColegiatura() {
        this.colegiaturaPendiente = 0;
    }

    public static void clientesPredefinidos() {
        clientes.add(new Cliente("Alejandro", 1606));
        clientes.add(new Cliente("Rose", 2051));
    }

    public static Cliente buscarClientePorPin(int pin) {
        for (Cliente cliente : clientes) {
            if (cliente.getPin() == pin) {
                return cliente;
            }
        }
        return null;
    }

    public static Cliente registrarCliente() {
        try {
            System.out.print("Bot: ¿Cuál es tu nombre completo? ");
            String nombre = teclado.nextLine().toUpperCase();

            int pin = 0;
            boolean pinValido = false;

            while (!pinValido) {
                System.out.print("Bot: Ingrese un PIN de 4 dígitos: ");
                try {
                    pin = teclado.nextInt();
                    teclado.nextLine();
                    if (pin >= 1000 && pin <= 9999) {// Verificar que el PIN sea de 4 dígitos
                        // Verificar que el PIN no esté ya en uso
                        if (buscarClientePorPin(pin) != null) {
                            System.out.println("Error: Este PIN ya está en uso. Intente con otro PIN.");
                        } else {
                            pinValido = true;
                        }
                    } else {
                        System.out.println("Error: El PIN debe ser de 4 dígitos.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Entrada no válida, por favor ingrese un número.");
                    teclado.nextLine();
                }
            }

            Cliente nuevoCliente = new Cliente(nombre, pin);
            clientes.add(nuevoCliente);
            return nuevoCliente;
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada no válida, por favor intente nuevamente.");
            teclado.nextLine(); // limpiar el buffer
            return null;
        }
    }

    public static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Bot: No hay clientes registrados.");
        } else {
            System.out.println("Lista de clientes registrados:");
            for (Cliente cliente : clientes) {
                System.out.println("Nombre: " + cliente.getNombre() + ", PIN: " + cliente.getPin());
            }
        }
    }

    public void crearCuenta() {
        try {
            System.out.print("Bot: Ingrese el saldo inicial: ");
            double saldo = teclado.nextDouble();
            teclado.nextLine(); // Limpiar el buffer

            System.out.println("Bot: Seleccione la moneda:");
            System.out.println("1. Bolivianos (Bs)");
            System.out.println("2. Dólares ($)");

            String moneda = null;
            while (moneda == null) {
                try {
                    System.out.print("Bot: Seleccione una opción: ");
                    int opcionMoneda = teclado.nextInt();
                    teclado.nextLine(); // Limpiar el buffer
                    if (opcionMoneda == 1) {
                        moneda = "Bs";
                    } else if (opcionMoneda == 2) {
                        moneda = "$";
                    } else {
                        System.out.println("Error: Por favor, seleccione una opción válida.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Entrada no válida, por favor intente nuevamente.");
                    teclado.nextLine(); // limpiar el buffer
                }
            }

            int numCuenta = (int) (Math.random() * 9000 + 1000); // Genera un número de cuenta de 4 dígitos
            Cuenta nuevaCuenta = new Cuenta(numCuenta, saldo, moneda);
            cuentas.add(nuevaCuenta);
            System.out.println("Bot: Cuenta creada exitosamente con número: " + numCuenta);
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada no válida, por favor intente nuevamente.");
            teclado.nextLine(); // limpiar el buffer
        }
    }

    public void listarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("Bot: No tienes ninguna cuenta creada.");
        } else {
            System.out.println("Cuentas:");
            for (Cuenta cuenta : cuentas) {
                System.out.println(cuenta);
            }
        }
    }

    public Cuenta buscarCuentaPorNumero(int numCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumCuenta() == numCuenta) {
                return cuenta;
            }
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPin() {
        return pin;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public List<Beneficiario> getBeneficiarios() {
        return beneficiarios;
    }

    public void agregarBeneficiario(Beneficiario beneficiario) {
        beneficiarios.add(beneficiario);
    }
}
