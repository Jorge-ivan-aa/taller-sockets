package co.edu.uniquindio.programacion3.taller_sockets.cliente;

import co.edu.uniquindio.programacion3.taller_sockets.dto.PedidoDto;
import co.edu.uniquindio.programacion3.taller_sockets.dto.Producto;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.List;

@Getter
@Setter
public class Cliente {

    // Cliente config
    String host;
    int puerto;
    Socket socketComunicacion;
    ObjectInputStream flujoEntradaObjeto;
    ObjectOutputStream flujoSalidaObjeto;
    DataInputStream msjEntrada;

    // Logica del negocio
    private CartaController cartaController;
    private List<Producto> carta;


    public Cliente(String host, int puerto) {
        this.puerto = puerto;
        this.host = host;
    }


    public void iniciarCliente() {
        Thread clienteThread = new Thread(() -> {
            try {
                crearConexion();
                flujoSalidaObjeto = new ObjectOutputStream(socketComunicacion.getOutputStream());
                flujoEntradaObjeto = new ObjectInputStream(socketComunicacion.getInputStream());
                msjEntrada = new DataInputStream(socketComunicacion.getInputStream());


                recibirCarta();
                recibirMsj();

            } catch (IOException | ClassNotFoundException e) {
                ViewTools.mostrarMensaje("¡Error de conexión!", "No se ha podido conectar con el servidor",
                        "Asegúrate de que el servidor está disponible.", Alert.AlertType.ERROR);
            }
        });
        clienteThread.start();
    }



    private void recibirMsj() {
        Thread hiloMsj = new Thread(() -> {
            try {
                while (true) {
                    String msj = msjEntrada.readUTF();
                    Platform.runLater(() -> {
                        cartaController.actualizarMsj(msj);
                    });
                }
            } catch (IOException e) {
                System.err.println("Error al recibir mensajes del servidor: " + e.getMessage());
            }
        });
        hiloMsj.setDaemon(true);
        hiloMsj.start();
    }


    @SuppressWarnings("unchecked")
    private void recibirCarta() throws IOException, ClassNotFoundException {
        System.out.println("... esperando carta.");
        List<Producto> carta = (List<Producto>) flujoEntradaObjeto.readObject();

        Platform.runLater(() -> {
            System.out.println("... carta resivida.");
            this.carta = carta;
            cartaController.actualizarComboBox(carta);
        });
    }

    private void crearConexion() throws IOException {
        socketComunicacion = new Socket(host, puerto);
        System.out.println("... conectado al servidor");
    }


    public void hacerPedido(PedidoDto pedidoDto) throws IOException {
        if (flujoSalidaObjeto != null) {
            flujoSalidaObjeto.writeObject(pedidoDto);
            flujoSalidaObjeto.flush();
        } else {
            System.err.println("El flujo de salida no está inicializado.");
        }
    }

}