package com.proinceringenieros.miniliga;

import com.proinceringenieros.miniliga.model.futbolista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class futbolistaController {

    // --- CAMPOS FORM ---
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;          // EN TU FXML: esto es SUELDO (float)
    @FXML private TextField txtTraspaso;      // EN TU FXML: traspaso (int)
    @FXML private CheckBox chkActivo;
    @FXML private DatePicker txtFechaNacimiento; // EN TU FXML: DatePicker

    @FXML private ComboBox<Integer> cmbEquipo;

    // --- ERRORES / MENSAJES ---
    @FXML private Label errId;
    @FXML private Label errNombre;
    @FXML private Label errEmail;    // EN TU FXML: lo usamos como error de FECHA NACIMIENTO
    @FXML private Label errEdad;     // EN TU FXML: lo usamos como error de SUELDO
    @FXML private Label errSaldo;    // EN TU FXML: lo usamos como error de TRASPASO
    @FXML private Label errEntidadB; // EN TU FXML: error de equipo
    @FXML private Label lblMensaje;
    @FXML private Label lblPosicion;

    // --- “DATASTORE” SIMPLE EN MEMORIA ---
    private final ObservableList<futbolista> lista = FXCollections.observableArrayList();
    private int index = -1;
    private int nextId = 1;

    @FXML
    public void initialize() {
        // Cargar equipos (IDs de ejemplo). Si tú tienes una clase Equipo, te lo adapto después.
        cmbEquipo.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        if (lista.isEmpty()) {
            onNuevo();
        } else {
            index = 0;
            show(lista.get(index));
            refreshPos();
        }
    }

    @FXML
    private void onNuevo() {
        clearErrors();

        txtId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtTraspaso.setText("");
        chkActivo.setSelected(true);
        txtFechaNacimiento.setValue(null);
        cmbEquipo.getSelectionModel().clearSelection();

        lblMensaje.setText("Nuevo registro listo.");
        // No cambiamos index aquí; el registro aún no existe
        refreshPos();
    }

    @FXML
    private void onGuardar() {
        clearErrors();

        futbolista f = readFormForCreate();
        if (f == null) return;

        lista.add(f);
        index = lista.size() - 1;

        show(f);
        lblMensaje.setText("Guardado correctamente.");
        refreshPos();
    }

    @FXML
    private void onModificar() {
        clearErrors();

        futbolista current = getCurrent();
        if (current == null) {
            lblMensaje.setText("No hay registro para modificar.");
            return;
        }

        futbolista edited = readFormForEdit(current.getId());
        if (edited == null) return;

        current.setNombre(edited.getNombre());
        current.setFechaNacimiento(edited.getFechaNacimiento());
        current.setSueldo(edited.getSueldo());
        current.setTraspaso(edited.getTraspaso());
        current.setActivo(edited.isActivo());
        current.setIdEquipo(edited.getIdEquipo());

        show(current);
        lblMensaje.setText("Modificado correctamente.");
        refreshPos();
    }

    @FXML
    private void onEliminar() {
        futbolista current = getCurrent();
        if (current == null) {
            lblMensaje.setText("No hay registro para eliminar.");
            return;
        }

        lista.remove(index);

        if (lista.isEmpty()) {
            index = -1;
            onNuevo();
            lblMensaje.setText("Eliminado. Lista vacía.");
            return;
        }

        index = Math.min(index, lista.size() - 1);
        show(lista.get(index));
        lblMensaje.setText("Eliminado.");
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

    // ---------------- HELPERS ----------------

    private futbolista getCurrent() {
        if (index < 0 || index >= lista.size()) return null;
        return lista.get(index);
    }

    private void show(futbolista f) {
        txtId.setText(String.valueOf(f.getId()));
        txtNombre.setText(f.getNombre());
        txtFechaNacimiento.setValue(f.getFechaNacimiento());
        txtEdad.setText(String.valueOf(f.getSueldo()));       // sueldo
        txtTraspaso.setText(String.valueOf(f.getTraspaso())); // traspaso
        chkActivo.setSelected(f.isActivo());
        cmbEquipo.getSelectionModel().select(Integer.valueOf(f.getIdEquipo()));
    }

    private futbolista readFormForCreate() {
        futbolista temp = readFormCommon(0);
        if (temp == null) return null;
        temp.setId(nextId++);
        return temp;
    }

    private futbolista readFormForEdit(int idActual) {
        futbolista temp = readFormCommon(idActual);
        if (temp == null) return null;
        temp.setId(idActual);
        return temp;
    }

    private futbolista readFormCommon(int id) {
        boolean ok = true;

        String nombre = txtNombre.getText();
        if (nombre == null || nombre.trim().isEmpty()) {
            errNombre.setText("Obligatorio");
            ok = false;
        }

        LocalDate fechaNac = txtFechaNacimiento.getValue();
        if (fechaNac == null) {
            errEmail.setText("Obligatorio"); // (en tu FXML está mal nombrado)
            ok = false;
        } else if (fechaNac.isAfter(LocalDate.now())) {
            errEmail.setText("No puede ser futura");
            ok = false;
        }

        Float sueldo = parseFloatSafe(txtEdad.getText());
        if (sueldo == null) {
            errEdad.setText("Número válido");
            ok = false;
        }

        Integer traspaso = parseIntSafe(txtTraspaso.getText());
        if (traspaso == null) {
            errSaldo.setText("Número válido");
            ok = false;
        }

        Integer idEquipo = cmbEquipo.getSelectionModel().getSelectedItem();
        if (idEquipo == null) {
            errEntidadB.setText("Selecciona un equipo");
            ok = false;
        }

        if (!ok) {
            lblMensaje.setText("Revisa los campos marcados.");
            return null;
        }

        return new futbolista(
                id,
                nombre.trim(),
                fechaNac,
                sueldo,
                traspaso,
                chkActivo.isSelected(),
                idEquipo
        );
    }

    private void clearErrors() {
        if (errId != null) errId.setText("");
        errNombre.setText("");
        errEmail.setText("");
        errEdad.setText("");
        errSaldo.setText("");
        errEntidadB.setText("");
    }

    private void refreshPos() {
        int total = lista.size();
        if (total == 0) lblPosicion.setText("0/0");
        else lblPosicion.setText((index + 1) + "/" + total);
    }

    private Integer parseIntSafe(String s) {
        try {
            if (s == null) return null;
            String t = s.trim() ;
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
            // Soporta coma o punto
            t = t.replace(",", ".");
            return Float.parseFloat(t);
        } catch (Exception e) {
            return null;
        }
    }
}
