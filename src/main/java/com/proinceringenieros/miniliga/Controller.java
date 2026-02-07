package com.proinceringenieros.miniliga;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML private StackPane contentPane;
    @FXML private Label lblEstado;

    @FXML
    public void initialize() {
        lblEstado.setText("Pantalla principal cargada.");
    }

    @FXML
    private void onAbrirFutbolistas() {
        loadCenter("futbolista-view.fxml", "CRUD Futbolistas");
    }

    @FXML
    private void onAbrirEquipos() {
        loadCenter("equipo-view.fxml", "CRUD Equipos");
    }

    @FXML
    private void onAbrirEntrenadores() {
        loadCenter("entrenador-view.fxml", "CRUD Entrenadores");
    }

    @FXML
    private void onAbrirTabla() {
        loadCenter("tabla-view.fxml", "Vista Tabla (TableView)");
    }

    @FXML
    private void onExportar() {
        // Voluntario: preparado, pero no implementado aquí
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Exportar");
        a.setHeaderText("Exportar (voluntario)");
        a.setContentText("Funcionalidad preparada. Se implementará en el apartado voluntario.");
        a.showAndWait();
    }

    @FXML
    private void onAcercaDe() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Acerca de");
        a.setHeaderText("MiniLiga");
        a.setContentText("Aplicación JavaFX - CRUD Futbolistas/Equipos/Entrenadores + TablaView.");
        a.showAndWait();
    }

    @FXML
    private void onSalir() {
        Stage stage = (Stage) contentPane.getScene().getWindow();
        stage.close();
    }

    // ----------------- helper -----------------

    private void loadCenter(String fxmlName, String estado) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlName));
            Parent root = loader.load();

            contentPane.getChildren().setAll(root);
            lblEstado.setText("Abierto: " + estado);
        } catch (IOException e) {
            lblEstado.setText("Error al abrir: " + fxmlName);
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("No se pudo cargar la vista: " + fxmlName);
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }
}
