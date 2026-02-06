package com.proinceringenieros.miniliga.model;

import java.io.Serializable;
import java.time.LocalDate;

public class entrenador implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;              // obligatorio
    private LocalDate fechaInicio;       // obligatorio
    private float sueldo;               // float
    private int equiposEntrenados;       // int
    private boolean principal;           // boolean

    public entrenador(int id, String nombre, LocalDate fechaInicio,
                      float sueldo, int equiposEntrenados, boolean principal) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.sueldo = sueldo;
        this.equiposEntrenados = equiposEntrenados;
        this.principal = principal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public float getSueldo() { return sueldo; }
    public void setSueldo(float sueldo) { this.sueldo = sueldo; }

    public int getEquiposEntrenados() { return equiposEntrenados; }
    public void setEquiposEntrenados(int equiposEntrenados) { this.equiposEntrenados = equiposEntrenados; }

    public boolean isPrincipal() { return principal; }
    public void setPrincipal(boolean principal) { this.principal = principal; }
}
