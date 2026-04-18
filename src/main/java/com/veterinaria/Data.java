package com.veterinaria;

public class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano){
        this.ano = ano;
        this.dia = dia;
        this.mes = mes;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public int getAno() {
        return ano;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }
    public int getDia() {
        return dia;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public int getMes() {
        return mes;
    }
    
}
