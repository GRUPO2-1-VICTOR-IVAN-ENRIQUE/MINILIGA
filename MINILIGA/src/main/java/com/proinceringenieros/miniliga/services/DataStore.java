package com.proinceringenieros.miniliga.services;

//import com.proinceringenieros.miniliga.model.futbolistas;
//import com.proinceringenieros.miniliga.model.entrenadores;
//import com.proinceringenieros.miniliga.model.equipos;




import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    //CREAR FICHERO FUTBOLISTAS

    private static final String FICHERO_FUTBOLISTAS =
            System.getProperty("user.home") + File.separator + "futbolistas.dat";

    //METODO FUTBOLISTAS GUARDAR

    public static void guardarFutbolistas(List<FUTBOLISTAS> futbolistas) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FICHERO_FUTBOLISTAS))) {

            //  Convertir ObservableList → ArrayList (serializable)

            oos.writeObject(new ArrayList<>(futbolistas));

        } catch (IOException e) {
            System.err.println("Error guardando futbolistas: " + e.getMessage());
        }
    }

    //METODO CARGAR FUTBOLISTAS

    @SuppressWarnings("unchecked")
    public static List<FUTBOLISTAS> cargarFutbolistas() {
        File fichero = new File(FICHERO_FUTBOLISTAS);

        if (!fichero.exists()) {
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fichero))) {

            return (List<FUTBOLISTAS>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando futbolistas: " + e.getMessage());
            return null;
        }
    }
    //CREO FICHERO PARA ENTRENADORES

    private static final String FICHERO_ENTRENADORES =
            System.getProperty("user.home") + File.separator + "entrenadores.dat";

    //ENTRENADORES METODO GUARDAR

    public static void guardarEntrenador(List<ENTRENADORES> entrenadores) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FICHERO_ENTRENADORES))) {

            //  Convertir ObservableList → ArrayList (serializable)
            oos.writeObject(new ArrayList<>(entrenadores));

        } catch (IOException e) {
            System.err.println("Error guardando entrenadores: " + e.getMessage());
        }
    }
    //METODO CARGAR ENTRENADORES
    @SuppressWarnings("unchecked")
    public static List<ENTRENADORES> cargarEntrenadores() {
        File fichero = new File(FICHERO_ENTRENADORES);

        if (!fichero.exists()) return null;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fichero))) {

            return (List<ENTRENADORES>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando entrenadores: " + e.getMessage());
            return null;
        }
    }
    //CREAR FICHERO EQUIPOS
    private static final String FICHERO_EQUIPOS =
            System.getProperty("user.home") + File.separator + "equipos.dat";

    //METODO GUARGAR EQUIPOS

    public static void guardarEQUIPOS(List<EQUIPOS> equipos) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FICHERO_EQUIPOS))) {
            //  Convertir ObservableList → ArrayList (serializable)
            oos.writeObject(new ArrayList<>(equipos));
        } catch (IOException e) {
            System.err.println("Error guardando equipos: " + e.getMessage());
        }
    }
    //METODO CARGAR EQUIPOS
    @SuppressWarnings("unchecked")
    public static List<EQUIPOS> cargarEquipos() {
        File f = new File(FICHERO_EQUIPOS);
        if (!f.exists()) return null;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(f))) {
            return (List<EQUIPOS>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando equipos: " + e.getMessage());
            return null;
        }
    }

}
