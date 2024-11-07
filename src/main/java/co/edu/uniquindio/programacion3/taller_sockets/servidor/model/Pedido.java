package co.edu.uniquindio.programacion3.taller_sockets.servidor.model;

import co.edu.uniquindio.programacion3.taller_sockets.dto.Producto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Pedido {
    private List<Producto> productos;
    private String cliente;
    private boolean listo;

    // Constructor
    public Pedido(String cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.listo = false;
    }

}

