package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by Michael Estrada on 11/1/17.
 */

public class IdRespuesta implements Serializable{
    private IdPregunta idPregunta;

    private Integer id_respuesta;

    public IdRespuesta(IdPregunta idPregunta, Integer id_respuesta) {
        this.idPregunta = idPregunta;
        this.id_respuesta = id_respuesta;
    }

    public IdPregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(IdPregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Integer getId_respuesta() {
        return id_respuesta;
    }

    public void setId_respuesta(Integer id_respuesta) {
        this.id_respuesta = id_respuesta;
    }
}
