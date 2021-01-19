package com.example.kbelo.models;

public class Instante {
    private int hora, minuos;

    public Instante(int hora, int minuos) {
        this.hora = hora;
        this.minuos = minuos;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuos() {
        return minuos;
    }

    public void setMinuos(int minuos) {
        this.minuos = minuos;
    }
}
