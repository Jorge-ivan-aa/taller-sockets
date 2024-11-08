package co.edu.uniquindio.programacion3.taller_sockets.servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import co.edu.uniquindio.programacion3.taller_sockets.dto.PedidoDto;
import co.edu.uniquindio.programacion3.taller_sockets.servidor.mapper.PedidoMapper;
import co.edu.uniquindio.programacion3.taller_sockets.servidor.model.Cafeteria;
import co.edu.uniquindio.programacion3.taller_sockets.servidor.model.Pedido;

public class Servidor {

    // Configuración del servidor
    String host = "localhost";
    int puerto = 8085;
    ServerSocket server;
    Socket socketComunicacion;
    ObjectOutputStream flujoSalida;
    ObjectInputStream flujoEntrada;
    DataOutputStream msjSalida;
    DataInputStream msjEntrada;

    // Logica del negocio
    private boolean primerRecorrido;
    private final Cafeteria cafeteria;
    private Pedido pedido;

    public Servidor() {
        this.cafeteria = new Cafeteria();
        this.primerRecorrido = true;
    }

    public void iniciarServidor() {
        try {
            server = new ServerSocket(puerto);
            while (true) {
                System.out.println("... esperando al cliente.");
                socketComunicacion = server.accept();
                System.out.println("... se aceptó la conexión.");

                flujoSalida = new ObjectOutputStream(socketComunicacion.getOutputStream());
                flujoEntrada = new ObjectInputStream(socketComunicacion.getInputStream());

                enviarCarta();
                while (true) {
                    System.out.println("... se espera un pedido");
                    recibirPedido();
                    System.out.println("... se va a enviar un pedido");
                    msjSalida = new DataOutputStream(socketComunicacion.getOutputStream());
                    notificarPedidoListo();
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
            if (msjSalida != null) msjSalida.close();
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

    private void notificarPedidoListo() throws IOException {

            if (pedido != null && pedido.isListo()) {
                msjSalida.writeUTF(pedido.toString());
                msjSalida.flush();
                System.out.println("\n... factura enviada al cliente. \n\n" + pedido + "\n\n\n");
                pedido.setListo(false);
            }

    }


    private void recibirPedido() {
        try {
            PedidoDto pedidoDto = (PedidoDto) flujoEntrada.readObject();
            pedido = PedidoMapper.dtoToPedido(pedidoDto, pedido);

            cafeteria.procesarPedido(pedido);
            System.out.println(pedido.toString());


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}