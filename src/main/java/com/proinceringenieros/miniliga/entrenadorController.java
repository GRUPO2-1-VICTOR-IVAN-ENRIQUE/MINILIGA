package com.proinceringenieros.miniliga;

import com.proinceringenieros.miniliga.model.entrenador;
import com.proinceringenieros.miniliga.model.equipo;
import com.proinceringenieros.miniliga.services.DataStore2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class entrenadorController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private DatePicker dpFechaInicio;
    @FXML private TextField txtSueldo;
    @FXML private TextField txtEquiposEntrenados;
    @FXML private CheckBox chkPrincipal;

    @FXML private ComboBox<Integer> cmbIdEntrenador;

    @FXML private Label errNombre;
    @FXML private Label errFechaInicio;
    @FXML private Label errSueldo;
    @FXML private Label errEquiposEntrenados;
    @FXML private Label errIdEquipo;

    @FXML private Label lblMensaje;
    @FXML private Label lblPosicion;
    private final DataStore2 ds = DataStore2.getInstance();
    private final ObservableList<entrenador> lista = FXCollections.observableArrayList();
    private int index = -1;
    private int nextId = 1;

    @FXML
    public void initialize() {
        // ids sugeridos (para que la rama compile sola sin DataStore)
        lista.setAll(ds.getEntrenadores());
        refreshEntrenadorCombo();

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
        dpFechaInicio.setValue(null);
        txtSueldo.setText("");
        txtEquiposEntrenados.setText("");
        chkPrincipal.setSelected(false);
        if (cmbIdEntrenador != null) cmbIdEntrenador.getSelectionModel().clearSelection();

        lblMensaje.setText("Nuevo entrenador listo.");
        refreshPos();
    }

    @FXML
    private void onGuardar() {
        clearErrors();

        entrenador e = readFormForCreate();
        if (e == null) return;
        ds.getEntrenadores().add(e);
        lista.setAll(ds.getEntrenadores());
        index = lista.size() - 1;
        show(lista.get(index));

        DataStore2.save();

        lblMensaje.setText("Entrenador guardado.");
        refreshPos();
    }

    @FXML
    private void onModificar() {
        clearErrors();

        entrenador current = getCurrent();
        if (current == null) {
            lblMensaje.setText("No hay entrenador para modificar.");
            return;
        }

        entrenador edited = readFormForEdit(current.getId());
        if (edited == null) return;

        current.setNombre(edited.getNombre());
        current.setFechaInicio(edited.getFechaInicio());
        current.setSueldo(edited.getSueldo());
        current.setEquiposEntrenados(edited.getEquiposEntrenados());
        current.setPrincipal(edited.isPrincipal());
        current.setIdEquipo(edited.getIdEquipo());

        show(current);
        DataStore2.save();
        lblMensaje.setText("Entrenador modificado.");
        refreshPos();
    }

    @FXML
    private void onEliminar() {
        entrenador current = getCurrent();
        if (current == null) {
            lblMensaje.setText("No hay entrenador para eliminar.");
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
        DataStore2.save();
        lblMensaje.setText("Entrenador eliminado.");
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

    // ----------------- Helpers -----------------

    private entrenador getCurrent() {
        if (index < 0 || index >= lista.size()) return null;
        return lista.get(index);
    }

    private void show(entrenador e) {
        txtId.setText(String.valueOf(e.getId()));
        txtNombre.setText(e.getNombre());
        dpFechaInicio.setValue(e.getFechaInicio());
        txtSueldo.setText(String.valueOf(e.getSueldo()));
        txtEquiposEntrenados.setText(String.valueOf(e.getEquiposEntrenados()));
        chkPrincipal.setSelected(e.isPrincipal());

        if (cmbIdEntrenador != null) {
            // intenta seleccionar; si no está en la lista, lo escribe en editor (editable)
            Integer idEq = e.getIdEquipo();
            if (cmbIdEntrenador.getItems().contains(idEq)) cmbIdEntrenador.getSelectionModel().select(idEq);
            else {
                cmbIdEntrenador.getSelectionModel().clearSelection();
                cmbIdEntrenador.getEditor().setText(String.valueOf(idEq));
            }
        }
    }
    private void refreshEntrenadorCombo() {
        if (cmbIdEntrenador == null) return;

        List<Integer> ids = ds.getEquipos().stream().map(equipo::getId).toList();
        cmbIdEntrenador.setItems(FXCollections.observableArrayList(ids));
        cmbIdEntrenador.setEditable(true);
    }
    private entrenador readFormForCreate() {
        entrenador temp = readFormCommon(0);
        if (temp == null) return null;
        temp.setId(nextId++);
        return temp;
    }

    private entrenador readFormForEdit(int idActual) {
        entrenador temp = readFormCommon(idActual);
        if (temp == null) return null;
        temp.setId(idActual);
        return temp;
    }

    private entrenador readFormCommon(int id) {
        boolean ok = true;

        String nombre = txtNombre.getText();
        if (nombre == null || nombre.trim().isEmpty()) {
            errNombre.setText("Obligatorio");
            ok = false;
        }

        LocalDate inicio = dpFechaInicio.getValue();
        if (inicio == null) {
            errFechaInicio.setText("Obligatorio");
            ok = false;
        } else if (inicio.isAfter(LocalDate.now())) {
            errFechaInicio.setText("No puede ser futura");
            ok = false;
        }

        Float sueldo = parseFloatSafe(txtSueldo.getText());
        if (sueldo == null) {
            errSueldo.setText("Número válido");
            ok = false;
        } else if (sueldo < 0) {
            errSueldo.setText("No puede ser negativo");
            ok = false;
        }

        Integer eq = parseIntSafe(txtEquiposEntrenados.getText());
        if (eq == null) {
            errEquiposEntrenados.setText("Número válido");
            ok = false;
        } else if (eq < 0) {
            errEquiposEntrenados.setText("No puede ser negativo");
            ok = false;
        }

        Integer idEquipo = readIdEquipo();
        if (idEquipo == null || idEquipo <= 0) {
            errIdEquipo.setText("Obligatorio (>0)");
            ok = false;
        }

        if (!ok) {
            lblMensaje.setText("Revisa los campos marcados.");
            return null;
        }

        return new entrenador(
                id,
                nombre.trim(),
                inicio,
                sueldo,
                eq,
                chkPrincipal.isSelected(),
                idEquipo
        );
    }

    private Integer readIdEquipo() {
        Object v = cmbIdEntrenador.getValue();
        if (v == null) return null;

        // Si el combo devuelve "3 - Barcelona" o "3"
        String s = v.toString().trim();
        if (s.isEmpty()) return null;

        try {
            String idStr = s.contains("-") ? s.split("-")[0].trim() : s;
            return Integer.parseInt(idStr);
        } catch (Exception ex) {
            System.err.println("ID equipo inválido en combo: " + s);
            return null;
        }
    }

    private void clearErrors() {
        errNombre.setText("");
        errFechaInicio.setText("");
        errSueldo.setText("");
        errEquiposEntrenados.setText("");
        errIdEquipo.setText("");
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

