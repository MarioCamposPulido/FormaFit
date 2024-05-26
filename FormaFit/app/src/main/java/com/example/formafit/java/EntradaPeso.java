package com.example.formafit.java;

import android.graphics.Bitmap;

import com.example.formafit.activities.MainActivity;

import java.util.Date;

public class EntradaPeso {

    private String fecha;
    private int altura;
    private int peso;
    private int edad;
    private String comentario;
    private Bitmap imagen;
    private String genero;

    public EntradaPeso(String fecha, int altura, int peso, int edad, String comentario, Bitmap imagen, String genero) {
        this.fecha = fecha;
        this.altura = altura;
        this.peso = peso;
        this.edad = edad;
        this.comentario = comentario;
        this.imagen = imagen;
        this.genero = genero;
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

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getImc(){
        return Math.floor((this.peso / Math.pow(this.altura / 100.0, 2) * 10)) / 10;
    }

    public double getPorcentajeGrasa(){
        switch (this.genero) {
            case "M":
                double grasaCorporalHombre = Math.floor((1.20 * getImc() + 0.23 * this.edad - 16.2) * 10) / 10;
                if (grasaCorporalHombre > 7 ){
                    return grasaCorporalHombre;
                } else {
                    return 7.0;
                }
            case "F":
                double grasaCorporalMujer = Math.floor((1.20 * getImc() + 0.23 * this.edad - 5.4) * 10) / 10;
                if (grasaCorporalMujer > 10 ){
                    return grasaCorporalMujer;
                } else {
                    return 10.0;
                }
            default:
                return -1.0;
        }
    }

    @Override
    public String toString() {
        return "EntradaPeso{" +
                "fecha='" + fecha + '\'' +
                ", altura=" + altura +
                ", peso=" + peso +
                ", edad=" + edad +
                ", comentario='" + comentario + '\'' +
                ", imagen=" + imagen +
                ", genero='" + genero + '\'' +
                '}';
    }
}
