package co.edu.uniquindio.programacion3.taller_sockets.cliente;

import co.edu.uniquindio.programacion3.taller_sockets.AppCliente;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class ViewTools {

    /**
     * Muestra un mensaje para dar información del usuario
     *
     * @param title   El título de la ventana.
     * @param header  Subtitulo de la ventana.
     * @param message Mensaje que describe la información a dar
     * @param type    El tipo de mensaje: `Alert.Alertype.<Enumeracion>`, donde Enumeracion puede ser:
     *                NONE,
     *                INFORMATION,
     *                WARNING,
     *                CONFIRMATION,
     *                ERROR;
     */

    public static void mostrarMensaje(String title, String header, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static Scene cargarEscena(String url, String... styles) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AppCliente.class.getResource(url));
            Scene scene = new Scene(fxmlLoader.load());
            for (String style : styles) {
                scene.getStylesheets().add(Objects.requireNonNull(AppCliente.class.getResource(style)).toExternalForm());
            }
            return scene;
        } catch (IOException e) {
            mostrarMensaje("Lo sentimos", "¡Ha ocurrido un error!", "La ventana no pudo cargar de forma adecuada, comunicate con atención tecnica", Alert.AlertType.ERROR);
            return new Scene(new Pane(), 600, 400);
        } catch (Exception e) {
            return new Scene(new Pane(), 600, 400);
        }
    }


    /**
     * Cierra la ventana actual a partir de un nodo de contexto.
     *
     * @param context Nodo dentro de la ventana que se desea cerrar.
     */
    public static void cerrarVentana(Node context) {
        Stage stage = (Stage) (context).getScene().getWindow();
        stage.close();
    }


    /**
     * Limpia el texto y el texto de sugerencia (prompt) de uno o más campos de texto.
     *
     * @param campoDeTexto Los campos de texto que se desean limpiar.
     */
    public static void limpiarCampos(TextInputControl... campoDeTexto) {
        for (TextInputControl texto : campoDeTexto) {
            if (texto != null) {
                texto.setText("");
                texto.setPromptText("");
            }
        }
    }


    /**
     * Verifica si alguno de los campos de texto proporcionados está vacío.
     *
     * @param camposDeTexto Texto de los campos a verificar.
     * @return true si todos los campos contienen texto; false si alguno está vacío.
     */
    public static boolean NoHayCamposVacios(String... camposDeTexto) {
        for (String texto : camposDeTexto) {
            if (texto.isEmpty() || texto.equals(" ")) {
                return false;
            }
        }
        return true;
    }


    /**
     * Cambia la visibilidad entre paneles en una misma ventana.
     *
     * @param primario    El panel que se debe hacer visible.
     * @param duracion    Duración en segundos de la transición de desvanecimiento.
     * @param secundarios Los paneles que se deben ocultar.
     */
    public static void cambiarPantalla(Pane primario, double duracion, Pane... secundarios) {
        // Verificación simple para evitar errores
        if (primario == null || secundarios == null) {
            System.out.println("No se pudo cambiar de pantalla, algun panel es nulo.");

        } else {
            for (Pane secundario : secundarios) {
                secundario.setVisible(false);
                fadeOut(secundario, duracion);
            }
            primario.setVisible(true);
            fadeIn(primario, duracion);
        }

    }


    /**
     * Aplica una animación de desvanecimiento gradual a un nodo, haciéndolo desaparecer.
     *
     * @param node     Nodo al cual aplicar la animación.
     * @param duracion Duración en segundos de la animación.
     */
    public static void fadeOut(Node node, double duracion) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duracion), node);
        fadeTransition.setFromValue(1.0); // Opacidad inicial
        fadeTransition.setToValue(0.0);    // Opacidad final
        fadeTransition.play();
    }


    /**
     * Aplica una animación de desvanecimiento gradual a un nodo, haciéndolo aparecer.
     *
     * @param node     Nodo al cual aplicar la animación.
     * @param duracion Duración en segundos de la animación.
     */
    public static void fadeIn(Node node, double duracion) {
        node.setOpacity(0.0); // Asegúrate de que el nodo esté completamente invisible antes de iniciar
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duracion), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

}