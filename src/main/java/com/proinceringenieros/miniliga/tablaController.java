package com.proinceringenieros.miniliga;

import com.proinceringenieros.miniliga.model.entrenador;
import com.proinceringenieros.miniliga.model.equipo;
import com.proinceringenieros.miniliga.model.futbolista;
import com.proinceringenieros.miniliga.services.DataStore2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class tablaController {

    // Futbolistas
    @FXML private TableView<futbolista> tvFutbolistas;
    @FXML private TableColumn<futbolista, Integer> colFId;
    @FXML private TableColumn<futbolista, String> colFNombre;
    @FXML private TableColumn<futbolista, Object> colFFechaNac;
    @FXML private TableColumn<futbolista, Float> colFSueldo;
    @FXML private TableColumn<futbolista, Integer> colFTraspaso;
    @FXML private TableColumn<futbolista, Boolean> colFActivo;
    @FXML private TableColumn<futbolista, Integer> colFIdEquipo;

    // Equipos
    @FXML private TableView<equipo> tvEquipos;
    @FXML private TableColumn<equipo, Integer> colEId;
    @FXML private TableColumn<equipo, String> colENombre;
    @FXML private TableColumn<equipo, Object> colEFechaFund;
    @FXML private TableColumn<equipo, Float> colEPatrimonio;
    @FXML private TableColumn<equipo, Integer> colENumJug;
    @FXML private TableColumn<equipo, Boolean> colEClasificado;
    @FXML private TableColumn<equipo, Integer> colEIdJugador;
    @FXML private TableColumn<equipo, Integer> colEIdEntrenador;

    // Entrenadores
    @FXML private TableView<entrenador> tvEntrenadores;
    @FXML private TableColumn<entrenador, Integer> colTId;
    @FXML private TableColumn<entrenador, String> colTNombre;
    @FXML private TableColumn<entrenador, Object> colTFechaInicio;
    @FXML private TableColumn<entrenador, Float> colTSueldo;
    @FXML private TableColumn<entrenador, Integer> colTEquipos;
    @FXML private TableColumn<entrenador, Boolean> colTPrincipal;

    @FXML private Label lblInfo;

    private final DataStore2 ds = DataStore2.getInstance();

    private final ObservableList<futbolista> dataF = FXCollections.observableArrayList();
    private final ObservableList<equipo> dataE = FXCollections.observableArrayList();
    private final ObservableList<entrenador> dataT = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // --- Bind columnas a getters (PropertyValueFactory llama getX / isX) ---
        // Futbolistas (ajústalo si tus getters se llaman distinto)
        colFId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFFechaNac.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colFSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        colFTraspaso.setCellValueFactory(new PropertyValueFactory<>("traspaso"));
        colFActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        colFIdEquipo.setCellValueFactory(new PropertyValueFactory<>("idEquipo"));

        // Equipos
        colEId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colENombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEFechaFund.setCellValueFactory(new PropertyValueFactory<>("fechaFundacion"));
        colEPatrimonio.setCellValueFactory(new PropertyValueFactory<>("patrimonio"));
        colENumJug.setCellValueFactory(new PropertyValueFactory<>("numeroJugadores"));
        colEClasificado.setCellValueFactory(new PropertyValueFactory<>("clasificado"));
        colEIdJugador.setCellValueFactory(new PropertyValueFactory<>("idJugador"));
        colEIdEntrenador.setCellValueFactory(new PropertyValueFactory<>("idEntrenador"));

        // Entrenadores
        colTId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colTSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        colTEquipos.setCellValueFactory(new PropertyValueFactory<>("equiposEntrenados"));
        colTPrincipal.setCellValueFactory(new PropertyValueFactory<>("principal"));

        // Asignar listas
        tvFutbolistas.setItems(dataF);
        tvEquipos.setItems(dataE);
        tvEntrenadores.setItems(dataT);

        // Cargar datos iniciales
        recargar();
    }

    @FXML
    private void onRecargar() {
        recargar();
    }

    private void recargar() {
        dataF.setAll(ds.getFutbolistas());
        dataE.setAll(ds.getEquipos());
        dataT.setAll(ds.getEntrenadores());

        lblInfo.setText("Cargados: Futbolistas=" + dataF.size()
                + " | Equipos=" + dataE.size()
                + " | Entrenadores=" + dataT.size());
    }
}

