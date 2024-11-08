package co.edu.uniquindio.programacion3.taller_sockets.servidor.mapper;

import co.edu.uniquindio.programacion3.taller_sockets.dto.PedidoDto;
import co.edu.uniquindio.programacion3.taller_sockets.servidor.model.Pedido;

public class PedidoMapper {

    public static Pedido dtoToPedido(PedidoDto pedidoDto, Pedido pedido) {
        if (pedido == null) {
            pedido = new Pedido();
        }

        pedido.setCliente(pedidoDto.cliente());

        for (int i = 0; i < pedidoDto.cantidad(); i++) {
            pedido.getProductos().add(pedidoDto.producto());
        }

        return pedido;
    }
}