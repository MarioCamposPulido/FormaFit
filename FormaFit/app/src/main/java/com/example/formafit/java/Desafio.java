package com.example.formafit.java;

import android.graphics.Bitmap;

public class Desafio {
    private String titulo;
    private String descripcion;
    private Bitmap img;
    private int is_checked;

    public Desafio(String titulo, String descripcion, Bitmap img, int is_checked) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.img = img;
        this.is_checked = is_checked;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getIs_checked() {
        return is_checked;
    }

    public void setIs_checked(int is_checked) {
        this.is_checked = is_checked;
    }

    @Override
    public String toString() {
        return "Desafio{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", img=" + img +
                ", is_checked=" + is_checked +
                '}';
    }
}
