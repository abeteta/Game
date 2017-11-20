package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by Michael Estrada on 11/19/17.
 */

public class EstadoUsuario implements Serializable {

    private Integer estado;

    public EstadoUsuario (){super();}

    public EstadoUsuario(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
