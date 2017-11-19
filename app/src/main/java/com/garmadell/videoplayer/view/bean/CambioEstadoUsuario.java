package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by alex on 11/19/17.
 */

public class CambioEstadoUsuario implements Serializable {
    private Integer id_usuario;

    public CambioEstadoUsuario (){super();}

    public CambioEstadoUsuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
}
