package com.proinceringenieros.miniliga.model;

import java.io.Serializable;
import java.time.LocalDate;

public class equipo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;                 // obligatorio
    private LocalDate fechaFundacion;      // obligatorio
    private float patrimonio;              // float
    private int numeroJugadores;           // int
    private boolean clasificado;           // boolean

    // FKs opcionales en UI (ComboBox puede estar sin seleccionar)
    private Integer idJugador;             // puede ser null
    private Integer idEntrenador;          // puede ser null

    public equipo(int id, String nombre, LocalDate fechaFundacion,
                  float patrimonio, int numeroJugadores,
                  boolean clasificado, Integer idJugador, Integer idEntrenador) {
        this.id = id;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.patrimonio = patrimonio;
        this.numeroJugadores = numeroJugadores;
        this.clasificado = clasificado;
        this.idJugador = idJugador;
        this.idEntrenador = idEntrenador;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaFundacion() { return fechaFundacion; }
    public void setFechaFundacion(LocalDate fechaFundacion) { this.fechaFundacion = fechaFundacion; }

    public float getPatrimonio() { return patrimonio; }
    public void setPatrimonio(float patrimonio) { this.patrimonio = patrimonio; }

    public int getNumeroJugadores() { return numeroJugadores; }
    public void setNumeroJugadores(int numeroJugadores) { this.numeroJugadores = numeroJugadores; }

    public boolean isClasificado() { return clasificado; }
    public void setClasificado(boolean clasificado) { this.clasificado = clasificado; }

    public Integer getIdJugador() { return idJugador; }
    public void setIdJugador(Integer idJugador) { this.idJugador = idJugador; }

    public Integer getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(Integer idEntrenador) { this.idEntrenador = idEntrenador; }
}

