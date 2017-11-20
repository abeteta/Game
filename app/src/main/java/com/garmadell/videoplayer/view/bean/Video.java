package com.garmadell.videoplayer.view.bean;

/**
 * Created by Michael Estrada on 7/10/2017.
 */

import java.io.Serializable;

public class Video implements Serializable {

    private static final long serialVersionUID = -4748969806546471208L;

    private Integer id;
    private String nombre;
    private String descripcion;
    private String urlVideo;
    private String urlPortada;
    private Integer tipoVideo;

    public Video() {
    }

    public Video(String nombre, String descripcion, String urlVideo, String urlPortada, Integer tipoVideo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlVideo = urlVideo;
        this.urlPortada = urlPortada;
        this.tipoVideo = tipoVideo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getUrlPortada() {
        return urlPortada;
    }

    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }

    public Integer getTipoVideo() {
        return tipoVideo;
    }

    public void setTipoVideo(Integer tipoVideo) {
        this.tipoVideo = tipoVideo;
    }
}
