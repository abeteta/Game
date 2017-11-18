package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by alex on 11/17/17.
 */

public class Perfil implements Serializable {

    private Integer id_user;
    private String nombre;
    private String apellido;
    private String email;
    private String desc_usertype;

    public Perfil (){super();}


    public Perfil(Integer id_user, String nombre, String apellido, String email, String desc_usertype) {
        this.id_user = id_user;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.desc_usertype = desc_usertype;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc_usertype() {
        return desc_usertype;
    }

    public void setDesc_usertype(String desc_usertype) {
        this.desc_usertype = desc_usertype;
    }
}
