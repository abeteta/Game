package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by alex on 11/1/17.
 */

public class Respuesta implements Serializable {

    private IdRespuesta idRespuesta;

    private String desc_respuesta;

    public Respuesta () {super();}

    public Respuesta(IdRespuesta idRespuesta, String desc_respuesta) {
        this.idRespuesta = idRespuesta;
        this.desc_respuesta = desc_respuesta;
    }

    public IdRespuesta getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(IdRespuesta idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getDesc_respuesta() {
        return desc_respuesta;
    }

    public void setDesc_respuesta(String desc_respuesta) {
        this.desc_respuesta = desc_respuesta;
    }
}
