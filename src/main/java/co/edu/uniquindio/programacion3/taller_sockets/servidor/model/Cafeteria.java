package co.edu.uniquindio.programacion3.taller_sockets.servidor.model;

import co.edu.uniquindio.programacion3.taller_sockets.dto.PedidoDto;
import co.edu.uniquindio.programacion3.taller_sockets.dto.Producto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cafeteria {
    private List<Producto> menu;

    // Constructor
    public Cafeteria() {
        this.menu = new ArrayList<>();
        cargarMenu();
    }

    private void cargarMenu() {
        menu.add(new Producto("Café", 1.50));
        menu.add(new Producto("Té", 1.20));
        menu.add(new Producto("Pastel", 2.00));
        menu.add(new Producto("Sandwich", 3.00));
    }

    public void procesarPedido(Pedido pedido) {
        try {
            Thread.sleep(3000);  // Simula un retraso de 3 segundos en la preparación
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pedido.setListo(true);
    }

}

