package com.example.formafit.java;

import java.util.Date;

public class EntradaPeso {

    private String fecha;
    private int altura;
    private int peso;
    private int edad;
    private String comentario;

    public EntradaPeso(String fecha, int altura, int peso, int edad, String comentario) {
        this.fecha = fecha;
        this.altura = altura;
        this.peso = peso;
        this.edad = edad;
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "EntradaPesoRecycler{" +
                "fecha=" + fecha +
                ", altura=" + altura +
                ", peso=" + peso +
                ", edad=" + edad +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
