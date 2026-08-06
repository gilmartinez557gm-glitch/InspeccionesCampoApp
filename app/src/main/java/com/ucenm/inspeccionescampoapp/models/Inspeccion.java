package com.ucenm.inspeccionescampoapp.models;

public class Inspeccion {

    private String Id;
    private String UsuarioId;
    private String Titulo;
    private String Descripcion;
    private String FechaInspeccion;
    private String Latitud;
    private String Longitud;
    private String Estado;


    public String getId() {
        return Id;
    }


    public String getUsuarioId() {
        return UsuarioId;
    }


    public String getTitulo() {
        return Titulo;
    }


    public String getDescripcion() {
        return Descripcion;
    }


    public String getFechaInspeccion() {
        return FechaInspeccion;
    }


    public String getLatitud() {
        return Latitud;
    }


    public String getLongitud() {
        return Longitud;
    }


    public String getEstado() {
        return Estado;
    }

}