package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by alex on 11/19/17.
 */

public class UsuariosActivos implements Serializable{

    private Integer id_usuario;
    private String nombre;

    public UsuariosActivos (){super();}

    public UsuariosActivos(Integer id_usuario, String nombre) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
