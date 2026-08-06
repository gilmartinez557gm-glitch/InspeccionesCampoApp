package com.ucenm.inspeccionescampoapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "SesionUsuario";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public SessionManager(Context context){

        preferences = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );

        editor = preferences.edit();

    }

    public void guardarUsuario(
            String id,
            String nombre,
            String correo,
            String rol
    ){

        editor.putString("Id", id);
        editor.putString("Nombre", nombre);
        editor.putString("Correo", correo);
        editor.putString("Rol", rol);

        editor.apply();
    }


    public String getId(){
        return preferences.getString("Id", "");
    }


    public String getNombre(){
        return preferences.getString("Nombre", "");
    }


    public String getCorreo(){
        return preferences.getString("Correo", "");
    }


    public String getRol(){
        return preferences.getString("Rol", "");
    }


    public void cerrarSesion(){

        editor.clear();
        editor.apply();

    }

}
