package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michael Estrada on 11/1/17.
 */

public class Pregunta implements Serializable{

    private IdPregunta id;
    private String desc_pregunta;
    private List<Respuesta> respuestas;

    public Pregunta () {super();}

    public Pregunta(IdPregunta id, String desc_pregunta, List<Respuesta> respuestas) {
        this.id = id;
        this.desc_pregunta = desc_pregunta;
        this.respuestas = respuestas;
    }

    public IdPregunta getId() {
        return id;
    }

    public void setId(IdPregunta id) {
        this.id = id;
    }

    public String getDesc_pregunta() {
        return desc_pregunta;
    }

    public void setDesc_pregunta(String desc_pregunta) {
        this.desc_pregunta = desc_pregunta;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
}
