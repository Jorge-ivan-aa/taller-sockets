package co.edu.uniquindio.programacion3.taller_sockets;

import co.edu.uniquindio.programacion3.taller_sockets.cliente.CartaController;
import co.edu.uniquindio.programacion3.taller_sockets.cliente.Cliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppCliente extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppCliente.class.getResource("carta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // Obtén el controlador generado por JavaFX
        CartaController controller = fxmlLoader.getController();

        stage.setTitle("Cafetería");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
