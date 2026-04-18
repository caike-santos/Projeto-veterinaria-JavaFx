package com.veterinaria;

public class Horario {
    private int hora;
    private int minutos;
    private boolean disponivel;

    public Horario(int hora, int minutos, boolean disponivel){
        this.disponivel = disponivel;
        this.hora = hora;
        this.minutos = minutos;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    public boolean getDisponivel(){
        return disponivel;
    }
    public int getHora() {
        return hora;
    }
    public void setHora(int hora) {
        this.hora = hora;
    }
    public int getMinutos() {
        return minutos;
    }
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
    
}
