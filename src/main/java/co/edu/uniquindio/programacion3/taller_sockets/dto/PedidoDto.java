package co.edu.uniquindio.programacion3.taller_sockets.dto;

import java.io.Serializable;

public record PedidoDto(Producto producto, String cliente, int cantidad) implements Serializable {

}
