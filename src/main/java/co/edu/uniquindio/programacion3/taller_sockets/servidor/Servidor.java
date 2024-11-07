package co.edu.uniquindio.programacion3.taller_sockets.servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import co.edu.uniquindio.programacion3.taller_sockets.dto.PedidoDto;
import co.edu.uniquindio.programacion3.taller_sockets.servidor.model.Cafeteria;
import jakarta.xml.ws.handler.MessageContext.Scope;

public class Servidor {

    // Configuración del servidor
    String host = "localhost";
    int puerto = 8085;
    ServerSocket server;
    Socket socketComunicacion;
    ObjectOutputStream flujoSalida;
    ObjectInputStream flujoEntrada;
    BufferedReader entrada;

    // Logica del negocio
    private boolean primerRecorrido;
    private final Cafeteria cafeteria;

    public Servidor() {
        this.cafeteria = new Cafeteria();
        this.primerRecorrido = true;
    }

    public void iniciarServidor() {
        try {
            server = new ServerSocket(puerto);
            while (true) {
                System.out.println("Esperando al cliente");
                socketComunicacion = server.accept();
                System.out.println("Se aceptó la conexión");

                flujoSalida = new ObjectOutputStream(socketComunicacion.getOutputStream());
                flujoEntrada = new ObjectInputStream(socketComunicacion.getInputStream());

                enviarCarta();

                while (true) {
                    System.out.println("...se espera un pedido");
                    recibirPedido();
                    System.out.println("...se va a enviar un pedido");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
    }

    private void cerrarRecursos() {
        try {
            if (flujoEntrada != null) flujoEntrada.close();
            if (flujoSalida != null) flujoSalida.close();
            if (socketComunicacion != null) socketComunicacion.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void enviarCarta() {
        try {
            flujoSalida.writeObject(cafeteria.getMenu());
            flujoSalida.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void enviarPedido() throws IOException {

        //    flujoSalida.writeObject(universidad);
    }

    private void recibirPedido() {
        try {
            PedidoDto pedidoDto = (PedidoDto) flujoEntrada.readObject();
            System.out.println("El pedido es: ");
            System.out.println(pedidoDto.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}