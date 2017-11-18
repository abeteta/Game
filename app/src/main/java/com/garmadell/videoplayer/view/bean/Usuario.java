package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by alex on 11/17/17.
 */

public class Usuario implements Serializable {

    private Integer id_user;
    private String nombre;
    private String apellido;
    private String email;
    private Integer id_usertype;

    public Usuario () {super();}

    public Usuario(Integer id_user, String nombre, String apellido, String email, Integer id_usertype) {
        this.id_user = id_user;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.id_usertype = id_usertype;
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

    public Integer getId_usertype() {
        return id_usertype;
    }

    public void setId_usertype(Integer id_usertype) {
        this.id_usertype = id_usertype;
    }
}
