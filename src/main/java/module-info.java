module co.edu.uniquindio.programacion3.taller_sockets {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.ws;
    requires static lombok;

    exports co.edu.uniquindio.programacion3.taller_sockets.dto;
    exports co.edu.uniquindio.programacion3.taller_sockets.cliente;
    opens co.edu.uniquindio.programacion3.taller_sockets.cliente to javafx.fxml;
    exports co.edu.uniquindio.programacion3.taller_sockets;
    opens co.edu.uniquindio.programacion3.taller_sockets to javafx.fxml;
}