package com.proinceringenieros.miniliga.model;

import java.io.Serializable;
import java.time.LocalDate;

public class futbolista implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;               // obligatorio
    private LocalDate fechaNacimiento;   // obligatorio
    private float sueldo;                // float >=0
    private int traspaso;                // int >=0
    private boolean activo;              // boolean
    private int idEquipo;                // obligatorio (pertenece a equipo)

    public futbolista(int id, String nombre, LocalDate fechaNacimiento, float sueldo,
                      int traspaso, boolean activo, int idEquipo) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldo = sueldo;
        this.traspaso = traspaso;
        this.activo = activo;
        this.idEquipo = idEquipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public float getSueldo() { return sueldo; }
    public void setSueldo(float sueldo) { this.sueldo = sueldo; }

    public int getTraspaso() { return traspaso; }
    public void setTraspaso(int traspaso) { this.traspaso = traspaso; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }


}

