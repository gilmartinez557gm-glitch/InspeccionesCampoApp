package com.ucenm.inspeccionescampoapp.models;

public class Usuario {

    private String Id;
    private String Nombre;
    private String Correo;
    private String Password;
    private String Rol;
    private String Estado;


    public String getId() {
        return Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getPassword() {
        return Password;
    }

    public String getRol() {
        return Rol;
    }

    public String getEstado() {
        return Estado;
    }
}