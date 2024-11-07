package co.edu.uniquindio.programacion3.taller_sockets.cliente;

import co.edu.uniquindio.programacion3.taller_sockets.dto.PedidoDto;
import co.edu.uniquindio.programacion3.taller_sockets.dto.Producto;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

                recibirCarta();

                // Ahora el cliente puede seguir interactuando sin cerrar los flujos
            } catch (IOException | ClassNotFoundException e) {
                ViewTools.mostrarMensaje("¡Error de conexión!", "No se ha podido conectar con el servidor",
                        "Asegúrate de que el servidor está disponible.", Alert.AlertType.ERROR);
            }
        });
        clienteThread.start();
    }


    @SuppressWarnings("unchecked")
    private void recibirCarta() throws IOException, ClassNotFoundException {
        System.out.println("Esperando carta...");
        List<Producto> carta = (List<Producto>) flujoEntradaObjeto.readObject();

        Platform.runLater(() -> {
            this.carta = carta;
            cartaController.actualizarComboBox(carta);
        });
    }

    private void crearConexion() throws IOException {
        socketComunicacion = new Socket(host, puerto);
        System.out.println("Conectado al servidor");
    }


    public void hacerPedido(PedidoDto pedidoDto) throws IOException {
        if (flujoSalidaObjeto != null) {
            flujoSalidaObjeto.writeObject(pedidoDto);
            flujoSalidaObjeto.flush();
            System.out.println("pasa por aqui");
        } else {
            System.err.println("El flujo de salida no está inicializado.");
        }
    }

}