package com.proinceringenieros.miniliga.services;

import com.proinceringenieros.miniliga.model.entrenador;
import com.proinceringenieros.miniliga.model.equipo;
import com.proinceringenieros.miniliga.model.futbolista;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataStore2 implements Serializable {
    private static final long serialVersionUID = 1L;


    private static transient DataStore2 instance;

    // Datos persistentes
    private List<futbolista> futbolistas = new ArrayList<>();
    private List<equipo> equipos = new ArrayList<>();
    private List<entrenador> entrenadores = new ArrayList<>();

    // Contadores IDs
    private int nextFutbolistaId = 1;
    private int nextEquipoId = 1;
    private int nextEntrenadorId = 1;


    private DataStore2() {}

    public static DataStore2 getInstance() {
        if (instance == null) instance = new DataStore2();
        return instance;
    }


    private static Path getFilePath() {
        Path dir = Path.of(System.getProperty("user.home"), ".miniliga");
        try { Files.createDirectories(dir); } catch (IOException e) { e.printStackTrace(); }
        return dir.resolve("datastore.ser");
    }


    private void ensureCollections() {
        if (futbolistas == null) futbolistas = new ArrayList<>();
        if (equipos == null) equipos = new ArrayList<>();
        if (entrenadores == null) entrenadores = new ArrayList<>();
    }


    private void sanitize() {
        futbolistas.removeIf(f -> f == null);
        equipos.removeIf(e -> e == null);
        entrenadores.removeIf(t -> t == null);
    }


    public static void load() {
        Path file = getFilePath();
        System.out.println("[DataStore2] Cargando desde: " + file.toAbsolutePath());

        if (!Files.exists(file)) {
            System.out.println("[DataStore2] No existe fichero. Se crea DataStore2 vacío.");
            instance = new DataStore2();
            instance.ensureCollections();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            instance = (DataStore2) ois.readObject();

            instance.ensureCollections();
            instance.sanitize();

            System.out.println("[DataStore2] Carga OK. Futbolistas: " + instance.getFutbolistas().size()
                    + " Equipos: " + instance.getEquipos().size()
                    + " Entrenadores: " + instance.getEntrenadores().size());

        } catch (Exception e) {
            System.err.println("[DataStore2] ERROR cargando. Se crea DataStore2 vacío.");
            e.printStackTrace();
            instance = new DataStore2();
            instance.ensureCollections();
        }
    }

    // Guardar (al salir)
    public static void save() {
        DataStore2 ds = getInstance();
        Path file = getFilePath();

        ds.ensureCollections();
        ds.sanitize();

        System.out.println("[DataStore2] Guardando en: " + file.toAbsolutePath()
                + " | Futbolistas=" + ds.getFutbolistas().size()
                + " Equipos=" + ds.getEquipos().size()
                + " Entrenadores=" + ds.getEntrenadores().size());

        try {
            Path parent = file.getParent();
            if (parent != null) Files.createDirectories(parent);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
                oos.writeObject(ds);
            }

            System.out.println("[DataStore2] Guardado OK. Existe fichero? " + Files.exists(file));
        } catch (IOException e) {
            System.err.println("[DataStore2] ERROR guardando en: " + file.toAbsolutePath());
            e.printStackTrace();
        }
    }

    // Mantener singleton tras deserialización
    private Object readResolve() {
        instance = this;
        return this;
    }

    // Getters
    public List<futbolista> getFutbolistas() { return futbolistas; }
    public List<equipo> getEquipos() { return equipos; }
    public List<entrenador> getEntrenadores() { return entrenadores; }

    // Generadores de ID
    public int nextFutbolistaId() { return nextFutbolistaId++; }
    public int nextEquipoId() { return nextEquipoId++; }
    public int nextEntrenadorId() { return nextEntrenadorId++; }
}



