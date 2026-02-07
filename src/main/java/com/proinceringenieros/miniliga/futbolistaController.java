package com.proinceringenieros.miniliga;

import com.proinceringenieros.miniliga.model.equipo;
import com.proinceringenieros.miniliga.model.futbolista;
import com.proinceringenieros.miniliga.services.DataStore2;
import com.proinceringenieros.miniliga.services.Validador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class futbolistaController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField txtSueldo;
    @FXML private TextField txtTraspaso;
    @FXML private CheckBox chkActivo;
    @FXML private ComboBox<Integer> cmbIdEquipo;

    @FXML private Label errNombre;
    @FXML private Label errFechaNacimiento;
    @FXML private Label errSueldo;
    @FXML private Label errTraspaso;
    @FXML private Label errIdEquipo;

    @FXML private Label lblMensaje;
    @FXML private Label lblPosicion;

    private final DataStore2 ds = DataStore2.getInstance();

    // Lista de trabajo ligada a DataStore
    private final ObservableList<futbolista> lista = FXCollections.observableArrayList();
    private int index = -1;

    @FXML
    public void initialize() {
        // cargar lista desde DataStore
        lista.setAll(ds.getFutbolistas());

        // cargar ids de equipos en combo
        refreshEquiposCombo();

        if (lista.isEmpty()) {
            onNuevo();
        } else {
            index = 0;
            show(lista.get(index));
            refreshPos();
        }
    }

    // ----------------- Acciones -----------------

    @FXML
    private void onNuevo() {
        clearErrors();
        txtId.setText("");
        txtNombre.setText("");
        dpFechaNacimiento.setValue(null);
        txtSueldo.setText("");
        txtTraspaso.setText("");
        chkActivo.setSelected(false);
        if (cmbIdEquipo != null) cmbIdEquipo.getSelectionModel().clearSelection();

        lblMensaje.setText("Nuevo futbolista listo.");
        refreshPos();
    }

    @FXML
    private void onGuardar() {
        clearErrors();

        futbolista f = readFormForCreate();
        if (f == null) return;

        // guardar en DataStore
        ds.getFutbolistas().add(f);

        // refrescar lista local y posicionar al final
        lista.setAll(ds.getFutbolistas());
        index = lista.size() - 1;
        show(lista.get(index));
        DataStore2.save();
        lblMensaje.setText("Futbolista guardado.");
        refreshPos();
    }

    @FXML
    private void onModificar() {
        clearErrors();

        futbolista current = getCurrent();
        if (current == null) {
            lblMensaje.setText("No hay futbolista para modificar.");
            return;
        }

        futbolista edited = readFormForEdit(current.getId());
        if (edited == null) return;

        // aplicar cambios sobre el objeto actual (que está en ds)
        current.setNombre(edited.getNombre());
        current.setFechaNacimiento(edited.getFechaNacimiento());
        current.setSueldo(edited.getSueldo());
        current.setTraspaso(edited.getTraspaso());
        current.setActivo(edited.isActivo());
        current.setIdEquipo(edited.getIdEquipo());

        // refrescar vista
        show(current);
        DataStore2.save();
        lblMensaje.setText("Futbolista modificado.");
        refreshPos();
    }

    @FXML
    private void onEliminar() {
        futbolista current = getCurrent();
        if (current == null) {
            lblMensaje.setText("No hay futbolista para eliminar.");
            return;
        }

        // borrar del DataStore
        ds.getFutbolistas().remove(current);

        // refrescar lista local
        lista.setAll(ds.getFutbolistas());

        if (lista.isEmpty()) {
            index = -1;
            onNuevo();
            lblMensaje.setText("Eliminado. Lista vacía.");
            return;
        }

        index = Math.min(index, lista.size() - 1);
        show(lista.get(index));
        DataStore2.save();
        lblMensaje.setText("Futbolista eliminado.");
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

    private futbolista getCurrent() {
        if (index < 0 || index >= lista.size()) return null;
        return lista.get(index);
    }

    private void show(futbolista f) {
        txtId.setText(String.valueOf(f.getId()));
        txtNombre.setText(f.getNombre());
        dpFechaNacimiento.setValue(f.getFechaNacimiento());
        txtSueldo.setText(String.valueOf(f.getSueldo()));
        txtTraspaso.setText(String.valueOf(f.getTraspaso()));
        chkActivo.setSelected(f.isActivo());

        if (cmbIdEquipo != null) {
            Integer idEq = f.getIdEquipo();
            if (cmbIdEquipo.getItems().contains(idEq)) cmbIdEquipo.getSelectionModel().select(idEq);
            else {
                cmbIdEquipo.getSelectionModel().clearSelection();
                cmbIdEquipo.getEditor().setText(String.valueOf(idEq));
            }
        }
    }

    private futbolista readFormForCreate() {
        futbolista temp = readFormCommon(0);
        if (temp == null) return null;
        temp.setId(ds.nextFutbolistaId());
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
        if (!Validador.futbolistaNombreOk(nombre)) {
            errNombre.setText("Obligatorio");
            ok = false;
        }

        LocalDate nac = dpFechaNacimiento.getValue();
        if (!Validador.futbolistaFechaNacimientoOk(nac)) {
            errFechaNacimiento.setText(nac == null ? "Obligatorio" : "No puede ser futura");
            ok = false;
        }

        Float sueldo = Validador.parseFloatSafe(txtSueldo.getText());
        if (!Validador.futbolistaSueldoOk(sueldo)) {
            errSueldo.setText(">= 0 (número válido)");
            ok = false;
        }

        Integer traspaso = Validador.parseIntSafe(txtTraspaso.getText());
        if (!Validador.futbolistaTraspasoOk(traspaso)) {
            errTraspaso.setText(">= 0 (número válido)");
            ok = false;
        }

        Integer idEquipo = readIdEquipo();
        if (!Validador.futbolistaIdEquipoOk(idEquipo)) {
            errIdEquipo.setText("Obligatorio (>0)");
            ok = false;
        } else if (!existsEquipo(idEquipo)) {
            // coherencia: que exista el equipo en DataStore
            errIdEquipo.setText("No existe ese equipo");
            ok = false;
        }

        if (!ok) {
            lblMensaje.setText("Revisa los campos marcados.");
            return null;
        }

        return new futbolista(
                id,
                nombre.trim(),
                nac,
                sueldo,
                traspaso,
                chkActivo.isSelected(),
                idEquipo
        );
    }

    private Integer readIdEquipo() {
        String value = String.valueOf(cmbIdEquipo.getValue());

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            // Espera formato: "ID - Nombre"
            String idStr = value.split("-")[0].trim();
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            System.err.println("Error leyendo ID de equipo: " + value);
            return null;
        }
    }


    private boolean existsEquipo(Integer idEquipo) {
        if (idEquipo == null) return false;
        for (equipo e : ds.getEquipos()) {
            if (e.getId() == idEquipo) return true;
        }
        return false;
    }

    private equipo[] getEquipos() {
        return null;
    }

    private void refreshEquiposCombo() {
        if (cmbIdEquipo == null) return;

        List<Integer> ids = ds.getEquipos().stream().map(equipo::getId).toList();
        cmbIdEquipo.setItems(FXCollections.observableArrayList(ids));
        cmbIdEquipo.setEditable(true);
    }

    private void clearErrors() {
        errNombre.setText("");
        errFechaNacimiento.setText("");
        errSueldo.setText("");
        errTraspaso.setText("");
        errIdEquipo.setText("");
    }

    private void refreshPos() {
        int total = lista.size();
        if (total == 0) lblPosicion.setText("0/0");
        else lblPosicion.setText((index + 1) + "/" + total);
    }
}
