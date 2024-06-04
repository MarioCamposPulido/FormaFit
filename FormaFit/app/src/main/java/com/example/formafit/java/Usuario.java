package com.example.formafit.java;

import androidx.annotation.NonNull;

/**
 * Class Usuario
 * Almacena datos del usuario y los organiza aquí
 */
public class Usuario {

    private String email;
    private String nombreUsuario;
    private String password;
    private String genero;
    private String nacimiento;
    private String altura;

    public Usuario(String email, String nombreUsuario, String password, String genero, String nacimiento, String altura) {
        this.email = email;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.genero = genero;
        this.nacimiento = nacimiento;
        this.altura = altura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    @NonNull
    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                ", genero='" + genero + '\'' +
                ", nacimiento='" + nacimiento + '\'' +
                ", altura='" + altura + '\'' +
                '}';
    }
}
