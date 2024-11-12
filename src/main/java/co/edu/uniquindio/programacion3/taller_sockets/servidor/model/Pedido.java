package co.edu.uniquindio.programacion3.taller_sockets.servidor.model;

import co.edu.uniquindio.programacion3.taller_sockets.dto.Producto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Pedido {
    private List<Producto> productos = new ArrayList<>();;
    private String cliente;
    private boolean listo;

    // Constructor
    public Pedido(String cliente) {
        this.cliente = cliente;
        this.listo = false;
    }

    @Override
    public String toString() {
        StringBuilder factura = new StringBuilder();
        factura.append("**********************\n");
        factura.append("     FACTURA DE PEDIDO\n");
        factura.append("**********************\n");
        factura.append("Cliente: ").append(cliente).append("\n\n");
        factura.append("Productos:\n");

        double total = 0;
        for (Producto producto : productos) {
            factura.append("- ").append(producto.nombre())
                    .append(" | Precio: $").append(producto.precio()).append("\n");
            total += producto.precio();
        }

        BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);


        factura.append("\nTotal: $").append(bd).append("\n");
        factura.append("Estado: ").append(listo ? "Listo para entrega" : "En preparación").append("\n");
        factura.append("**********************");

        return factura.toString();
    }

}

