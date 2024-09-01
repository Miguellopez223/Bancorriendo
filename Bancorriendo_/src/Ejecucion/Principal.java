package Ejecucion;

import Pago.Servicios;
import Pago.Transacciones;
import Pago.Transferencia;
import Usuario.Beneficiario;
import Usuario.Cliente;
import Usuario.Cuenta;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    private static Scanner teclado = new Scanner(System.in);
    private static Cliente clienteActual;

    public static void main(String[] args) {
        // Inicializar clientes predefinidos
        Cliente.clientesPredefinidos();
        menuInicio();
    }

    public static void menuInicio() {
        boolean condicion = true;

        do {
            System.out.println("-----BIENVENIDO A BANCORRIENDO-----");
            System.out.println("Bot: ¿Qué te gustaría hacer?");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrar Usuario");
            System.out.println("3. Listar Clientes");
            System.out.println("4. Cerrar Programa");

            try {
                System.out.print("Bot: Seleccione una opción: ");
                int sesion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer

                switch (sesion) {
                    case 1:
                        iniciarSesion();
                        break;
                    case 2:
                        registrarCliente();
                        break;
                    case 3:
                        listarClientes();
                        break;
                    case 4:
                        condicion = false;
                        System.out.println("Bot: Hasta luego!");
                        break;
                    default:
                        System.out.println("Error: Por favor elija una opción correcta.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor intente nuevamente.");
                teclado.nextLine(); // limpiar el buffer
            }
        } while (condicion);
    }

    public static void iniciarSesion() {
        clienteActual = null;
        try {
            System.out.print("Bot: Por favor, ingrese su PIN: ");
            int pin = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer

            clienteActual = Cliente.buscarClientePorPin(pin);
            if (clienteActual != null) {
                System.out.println("Bot: ¡Bienvenido " + clienteActual.getNombre() + "!");
                menuCliente();
            } else {
                System.out.println("Error: Cliente no encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada de Dato no válida, por favor intente nuevamente.");
            teclado.nextLine(); // limpiar el buffer
        }
    }

    public static void registrarCliente() {
        Cliente nuevoCliente = Cliente.registrarCliente();
        if (nuevoCliente != null) {
            System.out.println("Bot: Cliente registrado exitosamente.");
        }
    }

    public static void listarClientes() {
        Cliente.listarClientes();
    }

    public static void menuCliente() {
        boolean condicion = true;

        do {
            System.out.println("\nBot: ¿Qué te gustaría hacer?");
            System.out.println("1. Crear Cuenta");
            System.out.println("2. Listar Cuentas");
            System.out.println("3. Transferir Dinero entre Cuentas Propias");
            System.out.println("4. Transferencia a Terceros");
            System.out.println("5. Crear Beneficiario");
            System.out.println("6. Listar Beneficiarios");
            System.out.println("7. Transferir Dinero a Beneficiario");
            System.out.println("8. Pagar Servicios");
            System.out.println("9. Ver Extracto de Cuenta");
            System.out.println("10. Abonar Dinero");
            System.out.println("11. Debitar Dinero");
            System.out.println("0. Cerrar Sesión");

            try {
                System.out.print("Bot: Seleccione una opción: ");
                int opcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer

                switch (opcion) {
                    case 1:
                        clienteActual.crearCuenta();
                        break;
                    case 2:
                        clienteActual.listarCuentas();
                        break;
                    case 3:
                        transferirEntreCuentas();
                        break;
                    case 4:
                        transferenciaATerceros();
                        break;
                    case 5:
                        Beneficiario.crearBeneficiario(clienteActual);
                        break;
                    case 6:
                        Beneficiario.listarBeneficiarios(clienteActual);
                        break;
                    case 7:
                        transferirDinero();
                        break;
                    case 8:
                        pagarServicios();
                        break;
                    case 9:
                        verExtracto();
                        break;
                    case 10:
                        abonar();
                        break;
                    case 11:
                        debitar();
                        break;
                    case 0:
                        condicion = false;
                        clienteActual = null;
                        System.out.println("Bot: Sesión cerrada.");
                        break;
                    default:
                        System.out.println("Error: Por favor seleccione una opción válida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor intente nuevamente.");
                teclado.nextLine(); // limpiar el buffer
            }
        } while (condicion);
    }
    public static void pagarServicios() {
        if (clienteActual.getCuentas().isEmpty()) {
            System.out.println("Bot: No tienes cuentas para realizar un pago de servicios.");
            return;
        }

        System.out.println("\nBot: Seleccione el servicio que desea pagar:");
        System.out.println("1. Luz");
        System.out.println("2. Agua");
        System.out.println("3. Telecomunicaciones");
        System.out.println("4. Colegiaturas");

        int opcionServicio = 0;
        while (opcionServicio < 1 || opcionServicio > 4) {
            try {
                System.out.print("Bot: Ingrese el número del servicio: ");
                opcionServicio = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                if (opcionServicio < 1 || opcionServicio > 4) {
                    System.out.println("Error: Por favor seleccione una opción válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número del servicio.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        System.out.println("\nBot: Seleccione la cuenta desde la cual desea pagar el servicio:");
        clienteActual.listarCuentas();
        Cuenta cuentaSeleccionada = null;
        while (cuentaSeleccionada == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta: ");
                int numCuenta = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaSeleccionada = clienteActual.buscarCuentaPorNumero(numCuenta);
                if (cuentaSeleccionada == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Realizar el pago según el servicio seleccionado
        switch (opcionServicio) {
            case 1:
                Servicios.pagarLuz(clienteActual, cuentaSeleccionada);
                break;
            case 2:
                Servicios.pagarAgua(clienteActual, cuentaSeleccionada);
                break;
            case 3:
                Servicios.pagarTelecomunicaciones(clienteActual, cuentaSeleccionada);
                break;
            case 4:
                Servicios.pagarColegiatura(clienteActual, cuentaSeleccionada);
                break;
            default:
                System.out.println("Error: Opción no válida.");
                break;
        }
    }

    public static void verExtracto() {
        if (clienteActual.getCuentas().isEmpty()) {
            System.out.println("Bot: No tienes cuentas para ver el extracto.");
            return;
        }

        System.out.println("\nBot: Seleccione la cuenta para ver el extracto:");
        clienteActual.listarCuentas();
        Cuenta cuentaSeleccionada = null;
        while (cuentaSeleccionada == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta: ");
                int numCuenta = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaSeleccionada = clienteActual.buscarCuentaPorNumero(numCuenta);
                if (cuentaSeleccionada == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        cuentaSeleccionada.mostrarTransacciones();
    }

    public static void abonar() {
        if (clienteActual.getCuentas().isEmpty()) {
            System.out.println("Bot: No tienes cuentas para abonar dinero.");
            return;
        }

        System.out.println("\nBot: Seleccione la cuenta en la que desea abonar dinero:");
        clienteActual.listarCuentas();
        Cuenta cuentaSeleccionada = null;
        while (cuentaSeleccionada == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta: ");
                int numCuenta = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaSeleccionada = clienteActual.buscarCuentaPorNumero(numCuenta);
                if (cuentaSeleccionada == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        double monto = 0;
        boolean montoValido = false;
        while (!montoValido) {
            try {
                System.out.print("Bot: Ingrese el monto a abonar: ");
                monto = teclado.nextDouble();
                teclado.nextLine(); // Limpiar el buffer
                if (monto <= 0) {
                    System.out.println("Error: El monto debe ser mayor a 0.");
                } else {
                    montoValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un monto válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Llamar al método abonar de la clase Transacciones
        Transacciones.abonar(cuentaSeleccionada, monto);
    }

    public static void debitar() {
        if (clienteActual.getCuentas().isEmpty()) {
            System.out.println("Bot: No tienes cuentas para debitar dinero.");
            return;
        }

        System.out.println("\nBot: Seleccione la cuenta de la cual desea debitar dinero:");
        clienteActual.listarCuentas();
        Cuenta cuentaSeleccionada = null;
        while (cuentaSeleccionada == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta: ");
                int numCuenta = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaSeleccionada = clienteActual.buscarCuentaPorNumero(numCuenta);
                if (cuentaSeleccionada == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        double monto = 0;
        boolean montoValido = false;
        while (!montoValido) {
            try {
                System.out.print("Bot: Ingrese el monto a debitar: ");
                monto = teclado.nextDouble();
                teclado.nextLine(); // Limpiar el buffer
                if (monto <= 0) {
                    System.out.println("Error: El monto debe ser mayor a 0.");
                } else if (cuentaSeleccionada.getSaldo() < monto) {
                    System.out.println("Error: Saldo insuficiente en la cuenta.");
                } else {
                    montoValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un monto válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Llamar al método debitar de la clase Transacciones
        Transacciones.debitar(cuentaSeleccionada, monto);
    }

    public static void transferirEntreCuentas() {
        if (clienteActual.getCuentas().size() < 2) {
            System.out.println("Bot: Necesitas al menos dos cuentas para realizar una transferencia.");
            return;
        }

        System.out.println("\nBot: Seleccione la cuenta de origen:");
        clienteActual.listarCuentas();
        Cuenta cuentaOrigen = null;
        while (cuentaOrigen == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta de origen: ");
                int numCuentaOrigen = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaOrigen = clienteActual.buscarCuentaPorNumero(numCuentaOrigen);
                if (cuentaOrigen == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        System.out.println("\nBot: Seleccione la cuenta de destino:");
        Cuenta cuentaDestino = null;
        while (cuentaDestino == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta de destino: ");
                int numCuentaDestino = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaDestino = clienteActual.buscarCuentaPorNumero(numCuentaDestino);
                if (cuentaDestino == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                } else if (cuentaDestino.equals(cuentaOrigen)) {
                    System.out.println("Error: La cuenta de destino no puede ser la misma que la cuenta de origen.");
                    cuentaDestino = null;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        double monto = 0;
        boolean montoValido = false;
        while (!montoValido) {
            try {
                System.out.print("Bot: Ingrese el monto a transferir: ");
                monto = teclado.nextDouble();
                teclado.nextLine(); // Limpiar el buffer
                if (monto <= 0) {
                    System.out.println("Error: El monto debe ser mayor a 0.");
                } else if (cuentaOrigen.getSaldo() < monto) {
                    System.out.println("Error: Saldo insuficiente en la cuenta de origen.");
                } else {
                    montoValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un monto válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Realizar la transferencia
        Transferencia transferencia = new Transferencia();
        transferencia.transferir(cuentaOrigen, cuentaDestino, monto);
    }

    public static void transferirDinero() {
        if (clienteActual.getCuentas().isEmpty()) {
            System.out.println("Bot: No tienes cuentas para realizar una transferencia.");
            return;
        }

        // Seleccionar cuenta de origen
        System.out.println("\nBot: Seleccione la cuenta de origen:");
        clienteActual.listarCuentas();
        Cuenta cuentaOrigen = null;
        while (cuentaOrigen == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta de origen: ");
                int numCuentaOrigen = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaOrigen = clienteActual.buscarCuentaPorNumero(numCuentaOrigen);
                if (cuentaOrigen == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Seleccionar beneficiario
        if (clienteActual.getBeneficiarios().isEmpty()) {
            System.out.println("Bot: No tienes beneficiarios registrados.");
            return;
        }

        System.out.println("\nBot: Seleccione un beneficiario:");
        Beneficiario.listarBeneficiarios(clienteActual);
        Beneficiario beneficiarioSeleccionado = null;
        while (beneficiarioSeleccionado == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta del beneficiario: ");
                int numCuentaBeneficiario = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                beneficiarioSeleccionado = Beneficiario.buscarBeneficiarioPorNumero(clienteActual, numCuentaBeneficiario);
                if (beneficiarioSeleccionado == null) {
                    System.out.println("Error: Beneficiario no encontrado. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Ingresar monto a transferir
        double monto = 0;
        boolean montoValido = false;
        while (!montoValido) {
            try {
                System.out.print("Bot: Ingrese el monto a transferir: ");
                monto = teclado.nextDouble();
                teclado.nextLine(); // Limpiar el buffer
                if (monto <= 0) {
                    System.out.println("Error: El monto debe ser mayor a 0.");
                } else if (cuentaOrigen.getSaldo() < monto) {
                    System.out.println("Error: Saldo insuficiente en la cuenta de origen.");
                } else {
                    montoValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un monto válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Verificar que las monedas sean iguales
        if (!cuentaOrigen.getMoneda().equals(beneficiarioSeleccionado.getMoneda())) {
            System.out.println("Error: No se puede transferir entre cuentas con diferentes monedas.");
            return;
        }

        // Realizar la transferencia
        Transacciones.transferir(cuentaOrigen, beneficiarioSeleccionado, monto);
    }

    public static void transferenciaATerceros() {
        if (clienteActual.getCuentas().isEmpty()) {
            System.out.println("Bot: No tienes cuentas para realizar una transferencia.");
            return;
        }

        // Seleccionar cuenta de origen
        System.out.println("\nBot: Seleccione la cuenta de origen:");
        clienteActual.listarCuentas();
        Cuenta cuentaOrigen = null;
        while (cuentaOrigen == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta de origen: ");
                int numCuentaOrigen = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaOrigen = clienteActual.buscarCuentaPorNumero(numCuentaOrigen);
                if (cuentaOrigen == null) {
                    System.out.println("Error: Cuenta no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Seleccionar cliente destino
        System.out.println("\nBot: Seleccione el cliente destino:");
        listarClientes();
        Cliente clienteDestino = null;
        while (clienteDestino == null) {
            try {
                System.out.print("Bot: Ingrese el PIN del cliente destino: ");
                int pinClienteDestino = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                clienteDestino = Cliente.buscarClientePorPin(pinClienteDestino);
                if (clienteDestino == null) {
                    System.out.println("Error: Cliente destino no encontrado. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un PIN válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Seleccionar cuenta de destino
        System.out.println("\nBot: Seleccione la cuenta de destino:");
        clienteDestino.listarCuentas();
        Cuenta cuentaDestino = null;
        while (cuentaDestino == null) {
            try {
                System.out.print("Bot: Ingrese el número de cuenta de destino: ");
                int numCuentaDestino = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
                cuentaDestino = clienteDestino.buscarCuentaPorNumero(numCuentaDestino);
                if (cuentaDestino == null) {
                    System.out.println("Error: Cuenta destino no encontrada. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un número de cuenta válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Ingresar monto a transferir
        double monto = 0;
        boolean montoValido = false;
        while (!montoValido) {
            try {
                System.out.print("Bot: Ingrese el monto a transferir: ");
                monto = teclado.nextDouble();
                teclado.nextLine(); // Limpiar el buffer
                if (monto <= 0) {
                    System.out.println("Error: El monto debe ser mayor a 0.");
                } else if (cuentaOrigen.getSaldo() < monto) {
                    System.out.println("Error: Saldo insuficiente en la cuenta de origen.");
                } else {
                    montoValido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida, por favor ingrese un monto válido.");
                teclado.nextLine(); // limpiar el buffer
            }
        }

        // Realizar la transferencia entre clientes
        Transferencia transferencia = new Transferencia();
        transferencia.transferirEntreClientes(clienteActual, cuentaOrigen, clienteDestino, cuentaDestino.getNumCuenta(), monto);
    }
}
