package Pago;

import Usuario.Cliente;
import Usuario.Cuenta;

public class Transferencia {

    public void transferir(Cuenta origen, Cuenta destino, double monto) {
        if (!origen.getMoneda().equals(destino.getMoneda())) {
            System.out.println("Error: La transferencia solo se permite entre cuentas con la misma moneda.");
            return;
        }

        if (monto <= 0) {
            System.out.println("Error: El monto a transferir debe ser mayor a 0.");
            return;
        }

        if (origen.getSaldo() < monto) {
            System.out.println("Error: Saldo insuficiente en la cuenta de origen.");
            return;
        }

        // Realizar la transferencia
        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);

        String transaccion = "Transferencia de " + monto + " " + origen.getMoneda() +
                " de la cuenta " + origen.getNumCuenta() +
                " a la cuenta " + destino.getNumCuenta();
        origen.agregarTransaccion(transaccion);
        destino.agregarTransaccion(transaccion);

        System.out.println("Bot: Transferencia realizada con éxito de " + monto + " " +
                origen.getMoneda() + " desde la cuenta " + origen.getNumCuenta() +
                " a la cuenta " + destino.getNumCuenta());
    }

    public void transferirEntreClientes(Cliente clienteOrigen, Cuenta cuentaOrigen, Cliente clienteDestino, int numCuentaDestino, double monto) {
        // Buscar la cuenta de destino en el cliente destinatario
        Cuenta cuentaDestino = clienteDestino.buscarCuentaPorNumero(numCuentaDestino);
        if (cuentaDestino == null) {
            System.out.println("Error: Cuenta destino no encontrada en el cliente destino.");
            return;
        }

        // Verificar que la moneda sea la misma en ambas cuentas
        if (!cuentaOrigen.getMoneda().equals(cuentaDestino.getMoneda())) {
            System.out.println("Error: La transferencia solo se permite entre cuentas con la misma moneda.");
            return;
        }

        // Verificar que el monto sea válido
        if (monto <= 0) {
            System.out.println("Error: El monto a transferir debe ser mayor a 0.");
            return;
        }

        // Verificar saldo suficiente
        if (cuentaOrigen.getSaldo() < monto) {
            System.out.println("Error: Saldo insuficiente en la cuenta de origen.");
            return;
        }

        // Realizar la transferencia
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

        String transaccion = "Transferencia de " + monto + " " + cuentaOrigen.getMoneda() +
                " de la cuenta " + cuentaOrigen.getNumCuenta() +
                " a la cuenta " + cuentaDestino.getNumCuenta() +
                " del cliente " + clienteDestino.getNombre();
        cuentaOrigen.agregarTransaccion(transaccion);
        cuentaDestino.agregarTransaccion(transaccion);

        System.out.println("Bot: Transferencia realizada con éxito de " + monto + " " +
                cuentaOrigen.getMoneda() + " desde la cuenta " + cuentaOrigen.getNumCuenta() +
                " a la cuenta " + cuentaDestino.getNumCuenta() +
                " del cliente " + clienteDestino.getNombre());
    }
}
