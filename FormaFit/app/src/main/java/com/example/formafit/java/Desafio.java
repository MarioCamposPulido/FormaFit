package com.example.formafit.java;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

/**
 * Class Desafio
 * Ayuda a crear todos los datos de un desafío en una misma clase
 */
public class Desafio {
    private int id;
    private String titulo;
    private String descripcion;
    private Bitmap img;
    private int is_checked;

    public Desafio(int id, String titulo, String descripcion, Bitmap img, int is_checked) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.img = img;
        this.is_checked = is_checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "Desafio{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", img=" + img +
                ", is_checked=" + is_checked +
                '}';
    }
}
