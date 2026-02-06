package com.proinceringenieros.miniliga;

import com.proinceringenieros.miniliga.model.equipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class equipoController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private DatePicker dpFechaFundacion;
    @FXML private TextField txtPatrimonio;
    @FXML private TextField txtNumeroJugadores;
    @FXML private CheckBox chkClasificado;

    @FXML private ComboBox<Integer> cmbIdJugador;
    @FXML private ComboBox<Integer> cmbIdEntrenador;

    @FXML private Label errNombre;
    @FXML private Label errFechaFundacion;
    @FXML private Label errPatrimonio;
    @FXML private Label errNumeroJugadores;
    @FXML private Label errJugador;
    @FXML private Label errEntrenador;
    @FXML private Label lblMensaje;
    @FXML private Label lblPosicion;

    private final ObservableList<equipo> lista = FXCollections.observableArrayList();
    private int index = -1;
    private int nextId = 1;

    @FXML
    public void initialize() {
        // IDs de ejemplo (luego los cargarás de BD/DAO)
        cmbIdJugador.setItems(FXCollections.observableArrayList(1,2,3,4,5));
        cmbIdEntrenador.setItems(FXCollections.observableArrayList(1,2,3,4,5));

        if (lista.isEmpty()) onNuevo();
        else { index = 0; show(lista.get(index)); refreshPos(); }
    }

    @FXML
    private void onNuevo() {
        clearErrors();
        txtId.setText("");
        txtNombre.setText("");
        dpFechaFundacion.setValue(null);
        txtPatrimonio.setText("");
        txtNumeroJugadores.setText("");
        chkClasificado.setSelected(false);
        cmbIdJugador.getSelectionModel().clearSelection();
        cmbIdEntrenador.getSelectionModel().clearSelection();
        lblMensaje.setText("Nuevo equipo listo.");
        refreshPos();
    }

    @FXML
    private void onGuardar() {
        clearErrors();
        equipo e = readFormForCreate();
        if (e == null) return;

        lista.add(e);
        index = lista.size() - 1;
        show(e);
        lblMensaje.setText("Equipo guardado.");
        refreshPos();
    }

    @FXML
    private void onModificar() {
        clearErrors();
        equipo current = getCurrent();
        if (current == null) { lblMensaje.setText("No hay equipo para modificar."); return; }

        equipo edited = readFormForEdit(current.getId());
        if (edited == null) return;

        current.setNombre(edited.getNombre());
        current.setFechaFundacion(edited.getFechaFundacion());
        current.setPatrimonio(edited.getPatrimonio());
        current.setNumeroJugadores(edited.getNumeroJugadores());
        current.setClasificado(edited.isClasificado());
        current.setIdJugador(edited.getIdJugador());
        current.setIdEntrenador(edited.getIdEntrenador());

        show(current);
        lblMensaje.setText("Equipo modificado.");
        refreshPos();
    }

    @FXML
    private void onEliminar() {
        equipo current = getCurrent();
        if (current == null) { lblMensaje.setText("No hay equipo para eliminar."); return; }

        lista.remove(index);

        if (lista.isEmpty()) {
            index = -1;
            onNuevo();
            lblMensaje.setText("Eliminado. Lista vacía.");
            return;
        }

        index = Math.min(index, lista.size() - 1);
        show(lista.get(index));
        lblMensaje.setText("Equipo eliminado.");
        refreshPos();
    }

    @FXML
    private void onAnterior() {
        if (lista.isEmpty()) return;
        index = Math.max(0, index - 1);
        show(lista.get(index));
        refreshPos();
    }

    @FXML
    private void onSiguiente() {
        if (lista.isEmpty()) return;
        index = Math.min(lista.size() - 1, index + 1);
        show(lista.get(index));
        refreshPos();
    }

    // -------- helpers --------

    private equipo getCurrent() {
        if (index < 0 || index >= lista.size()) return null;
        return lista.get(index);
    }

    private void show(equipo e) {
        txtId.setText(String.valueOf(e.getId()));
        txtNombre.setText(e.getNombre());
        dpFechaFundacion.setValue(e.getFechaFundacion());
        txtPatrimonio.setText(String.valueOf(e.getPatrimonio()));
        txtNumeroJugadores.setText(String.valueOf(e.getNumeroJugadores()));
        chkClasificado.setSelected(e.isClasificado());

        if (e.getIdJugador() == null) cmbIdJugador.getSelectionModel().clearSelection();
        else cmbIdJugador.getSelectionModel().select(e.getIdJugador());

        if (e.getIdEntrenador() == null) cmbIdEntrenador.getSelectionModel().clearSelection();
        else cmbIdEntrenador.getSelectionModel().select(e.getIdEntrenador());
    }

    private equipo readFormForCreate() {
        equipo temp = readFormCommon(0);
        if (temp == null) return null;
        temp.setId(nextId++);
        return temp;
    }

    private equipo readFormForEdit(int idActual) {
        equipo temp = readFormCommon(idActual);
        if (temp == null) return null;
        temp.setId(idActual);
        return temp;
    }

    private equipo readFormCommon(int id) {
        boolean ok = true;

        String nombre = txtNombre.getText();
        if (nombre == null || nombre.trim().isEmpty()) {
            errNombre.setText("Obligatorio");
            ok = false;
        }

        LocalDate fund = dpFechaFundacion.getValue();
        if (fund == null) {
            errFechaFundacion.setText("Obligatorio");
            ok = false;
        } else if (fund.isAfter(LocalDate.now())) {
            errFechaFundacion.setText("No puede ser futura");
            ok = false;
        }

        Float patrimonio = parseFloatSafe(txtPatrimonio.getText());
        if (patrimonio == null) {
            errPatrimonio.setText("Número válido");
            ok = false;
        } else if (patrimonio < 0) {
            errPatrimonio.setText("No puede ser negativo");
            ok = false;
        }

        Integer numJug = parseIntSafe(txtNumeroJugadores.getText());
        if (numJug == null) {
            errNumeroJugadores.setText("Número válido");
            ok = false;
        } else if (numJug < 0) {
            errNumeroJugadores.setText("No puede ser negativo");
            ok = false;
        }

        // FKs opcionales: si el enunciado las obliga, lo cambiamos a obligatorio
        Integer idJugador = cmbIdJugador.getSelectionModel().getSelectedItem();
        Integer idEntrenador = cmbIdEntrenador.getSelectionModel().getSelectedItem();

        if (!ok) {
            lblMensaje.setText("Revisa los campos marcados.");
            return null;
        }

        return new equipo(
                id,
                nombre.trim(),
                fund,
                patrimonio,
                numJug,
                chkClasificado.isSelected(),
                idJugador,
                idEntrenador
        );
    }

    private void clearErrors() {
        errNombre.setText("");
        errFechaFundacion.setText("");
        errPatrimonio.setText("");
        errNumeroJugadores.setText("");
        if (errJugador != null) errJugador.setText("");
        if (errEntrenador != null) errEntrenador.setText("");
    }

    private void refreshPos() {
        int total = lista.size();
        if (total == 0) lblPosicion.setText("0/0");
        else lblPosicion.setText((index + 1) + "/" + total);
    }

    private Integer parseIntSafe(String s) {
        try {
            if (s == null) return null;
            String t = s.trim();
            if (t.isEmpty()) return null;
            return Integer.parseInt(t);
        } catch (Exception e) {
            return null;
        }
    }

    private Float parseFloatSafe(String s) {
        try {
            if (s == null) return null;
            String t = s.trim();
            if (t.isEmpty()) return null;
            t = t.replace(",", ".");
            return Float.parseFloat(t);
        } catch (Exception e) {
            return null;
        }
    }
}


