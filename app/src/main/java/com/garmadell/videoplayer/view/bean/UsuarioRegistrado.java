package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by Michael Estrada on 11/12/17.
 */

public class UsuarioRegistrado implements Serializable {

    private Boolean usuarioRegistrado;
    private Integer idUsuario;

    public UsuarioRegistrado (){super();}

    public UsuarioRegistrado(Boolean usuarioRegistrado, Integer idUsuario) {
        this.usuarioRegistrado = usuarioRegistrado;
        this.idUsuario = idUsuario;
    }

    public Boolean getUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    public void setUsuarioRegistrado(Boolean usuarioRegistrado) {
        this.usuarioRegistrado = usuarioRegistrado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
