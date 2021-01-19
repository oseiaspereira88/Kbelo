package com.example.kbelo.models;

public class Horario {
    private int diaDaSemana;
    private Instante inicio, fim;

    public Horario(int diaDaSemana, Instante inicio, Instante fim) {
        this.diaDaSemana = diaDaSemana;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(int diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public Instante getInicio() {
        return inicio;
    }

    public void setInicio(Instante inicio) {
        this.inicio = inicio;
    }

    public Instante getFim() {
        return fim;
    }

    public void setFim(Instante fim) {
        this.fim = fim;
    }
}
