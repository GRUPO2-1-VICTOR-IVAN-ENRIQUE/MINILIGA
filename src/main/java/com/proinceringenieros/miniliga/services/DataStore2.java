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

    // Singleton
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

    // Ruta del fichero (multiusuario)
    private static Path getFilePath() {
        String userHome = System.getProperty("user.home");
        Path dir = Path.of(userHome, ".miniliga");
        try { Files.createDirectories(dir); } catch (IOException ignored) {}
        return dir.resolve("datastore.ser");
    }

    // Cargar (al iniciar)
    public static void load() {
        Path file = getFilePath();
        if (!Files.exists(file)) {
            instance = new DataStore2();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            instance = (DataStore2) ois.readObject();
        } catch (Exception e) {
            instance = new DataStore2();
        }
    }

    // Guardar (al salir)
    public static void save() {
        DataStore2 ds = getInstance();
        Path file = getFilePath();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(ds);
        } catch (IOException ignored) {
        }
    }

    // Getters (los que esperan tus controllers)
    public List<futbolista> getFutbolistas() {
        return futbolistas;
    }
    public List<equipo> getEquipos() {
        return equipos;
    }
    public List<entrenador> getEntrenadores() {
        return entrenadores;
    }
    //public List<futbolista> getFutbolistas() { return futbolistas; }
    //public List<equipo> getEquipos() { return equipos; }
    //public List<entrenador> getEntrenadores() { return entrenadores; }

    // Generadores de ID
    public int nextFutbolistaId() { return nextFutbolistaId++; }
    public int nextEquipoId() { return nextEquipoId++; }
    public int nextEntrenadorId() { return nextEntrenadorId++; }
}


