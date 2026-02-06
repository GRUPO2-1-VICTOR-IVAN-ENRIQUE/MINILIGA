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

    public equipo(int id, String nombre, LocalDate fechaFundacion,
                  float patrimonio, int numeroJugadores, boolean clasificado) {
        this.id = id;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.patrimonio = patrimonio;
        this.numeroJugadores = numeroJugadores;
        this.clasificado = clasificado;
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
}
