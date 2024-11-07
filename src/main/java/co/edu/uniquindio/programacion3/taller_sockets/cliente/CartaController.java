package co.edu.uniquindio.programacion3.taller_sockets.cliente;

import co.edu.uniquindio.programacion3.taller_sockets.dto.PedidoDto;
import co.edu.uniquindio.programacion3.taller_sockets.dto.Producto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartaController {

    private final Cliente appCliente = new Cliente("localhost", 8085);
    private List<Producto> menu;


    @FXML
    private TextField txtCantidadProducto;

    @FXML
    private ComboBox<String> cbxCartaProducto;

    @FXML
    private Label lbNombreCliente;

    @FXML
    private TextArea txtaFactura;

    @FXML
    void ActionHacerPedido() {

        String[] StringPedido = cbxCartaProducto.getValue().split(":");
        PedidoDto pedidoDto = null;

        for (Producto producto: menu) {
            if (producto.nombre().equals(StringPedido[0])) {
                try {
                    pedidoDto = new PedidoDto(producto,
                            "Jorge",
                            Integer.parseInt(txtCantidadProducto.getText()));

                    appCliente.hacerPedido(pedidoDto);

                } catch (NumberFormatException ignore) {
                    ViewTools.mostrarMensaje("Cuidado", null, "La cantidad debe ser un valor numerico", Alert.AlertType.WARNING);
                } catch (IOException e) {
                    ViewTools.mostrarMensaje("Error", null, "No se pudo hacer el pedido, fallo en conexion.", Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
        }

        assert pedidoDto != null;
        System.out.println(pedidoDto.toString());

    }

    @FXML
    void ActionPagar() {

    }

    @FXML
    void initialize() {
        try {
            appCliente.setCartaController(this);
            System.out.println("Iniciando cliente\n");
            appCliente.iniciarCliente();

            List<Producto> carta = appCliente.getCarta();
            cbxCartaProducto.getItems().addAll(productoToString(carta));

        } catch (Exception ignore) {
            ViewTools.cerrarVentana(txtCantidadProducto);
        }

    }

    public void actualizarComboBox(List<Producto> carta) {
        Platform.runLater(() -> {
            if (carta != null && !carta.isEmpty()) {
                menu = carta;
                cbxCartaProducto.getItems().clear();
                cbxCartaProducto.getItems().addAll(productoToString(carta));
            }
        });
    }

    private static List<String> productoToString(List<Producto>  listaProductos) {
        List<String> lista = new ArrayList<>();
        StringBuilder cadena = new StringBuilder();

        if (listaProductos != null) {
            for (Producto producto : listaProductos) {
                cadena.append(producto.nombre())
                        .append(": $")
                        .append(producto.precio());

                lista.add(cadena.toString());
                cadena.setLength(0);
            }
        }

        return lista;
    }

}