package com.ucenm.inspeccionescampoapp.responses;

import com.ucenm.inspeccionescampoapp.models.Usuario;

public class LoginResponse {

    private String mensaje;
    private Usuario usuario;


    public String getMensaje() {
        return mensaje;
    }


    public Usuario getUsuario() {
        return usuario;
    }
}
