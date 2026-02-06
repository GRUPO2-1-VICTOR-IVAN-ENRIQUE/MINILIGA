package com.proinceringenieros.miniliga.services;

import java.time.LocalDate;

public class Validador {
    private Validador() {}

    // Básicos
    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static Integer parseIntSafe(String s) {
        try {
            if (s == null) return null;
            String t = s.trim();
            if (t.isEmpty()) return null;
            return Integer.parseInt(t);
        } catch (Exception e) {
            return null;
        }
    }

    public static Float parseFloatSafe(String s) {
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

    // Fechas
    public static boolean isFuture(LocalDate d) {
        return d != null && d.isAfter(LocalDate.now());
    }

    public static boolean isRequiredDateOk(LocalDate d) {
        return d != null && !isFuture(d);
    }

    // Números
    public static boolean isNonNegative(Integer n) {
        return n != null && n >= 0;
    }

    public static boolean isNonNegative(Float n) {
        return n != null && n >= 0;
    }

    public static boolean isPositive(Integer n) {
        return n != null && n > 0;
    }

    // FUTBOLISTA
    public static boolean futbolistaNombreOk(String nombre) {
        return !isBlank(nombre);
    }

    public static boolean futbolistaFechaNacimientoOk(LocalDate d) {
        return isRequiredDateOk(d);
    }

    public static boolean futbolistaSueldoOk(Float sueldo) {
        return isNonNegative(sueldo);
    }

    public static boolean futbolistaTraspasoOk(Integer traspaso) {
        return isNonNegative(traspaso);
    }

    public static boolean futbolistaIdEquipoOk(Integer idEquipo) {
        return isPositive(idEquipo); // obligatorio
    }

    // EQUIPO
    public static boolean equipoNombreOk(String nombre) {
        return !isBlank(nombre);
    }

    public static boolean equipoFechaFundacionOk(LocalDate d) {
        return isRequiredDateOk(d);
    }

    public static boolean equipoPatrimonioOk(Float patrimonio) {
        return isNonNegative(patrimonio);
    }

    public static boolean equipoNumeroJugadoresOk(Integer n) {
        return isNonNegative(n);
    }

    // ENTRENADOR
    public static boolean entrenadorNombreOk(String nombre) {
        return !isBlank(nombre);
    }

    public static boolean entrenadorFechaInicioOk(LocalDate d) {
        return isRequiredDateOk(d);
    }

    public static boolean entrenadorSueldoOk(Float sueldo) {
        return isNonNegative(sueldo);
    }

    public static boolean entrenadorEquiposEntrenadosOk(Integer n) {
        return isNonNegative(n);
    }

    public static boolean entrenadorIdEquipoOk(Integer idEquipo) {
        return isPositive(idEquipo); // obligatorio
    }
}









