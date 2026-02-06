package com.proinceringenieros.miniliga;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Controller {

    @FXML private StackPane contentPane;
    @FXML private Label lblStatus;

    @FXML
    public void initialize() {
        // Carga inicial: futbolistas
        cargarVista("futbolista-view.fxml", "Futbolistas");
    }

    @FXML private void onAbrirFutbolistas() { cargarVista("futbolista-view.fxml", "Futbolistas"); }
    @FXML private void onAbrirEquipos()     { cargarVista("equipo-view.fxml", "Equipos"); }
    @FXML private void onAbrirEntrenadores(){ cargarVista("entrenador-view.fxml", "Entrenadores"); }

    @FXML private void onAbrirTabla() {
        // Pendiente: tabla-view.fxml
        cargarVista("tabla-view.fxml", "TableView");
    }

    @FXML private void onExportarJson() {
        // Pendiente: exportación real
        lblStatus.setText("Exportar JSON (pendiente de implementar).");
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Exportar JSON: lo implementamos en el siguiente paso.");
        a.setHeaderText(null);
        a.showAndWait();
    }

    @FXML private void onAcercaDe() {
        Alert a = new Alert(Alert.AlertType.INFORMATION,
                "MiniLiga - Prácticas Presenciales 1ª Evaluación\nCRUD + Persistencia + TableView + Export JSON");
        a.setHeaderText("Acerca de");
        a.showAndWait();
    }

    @FXML private void onSalir() {
        // aquí podrías llamar a DataStore.saveAll()
        System.exit(0);
    }

    private void cargarVista(String fxml, String titulo) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxml));
            contentPane.getChildren().setAll(view);
            lblStatus.setText("Vista: " + titulo);
        } catch (Exception ex) {
            lblStatus.setText("Error cargando: " + fxml);
            Alert a = new Alert(Alert.AlertType.ERROR, "No se pudo cargar " + fxml + "\n" + ex.getMessage());
            a.setHeaderText("Error");
            a.showAndWait();
        }
    }
}

