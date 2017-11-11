package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by alex on 11/1/17.
 */

public class IdPregunta implements Serializable {

    private Integer id_curso;
    private Integer id_pregunta;

    public IdPregunta(Integer id_curso, Integer id_pregunta) {
        this.id_curso = id_curso;
        this.id_pregunta = id_pregunta;
    }

    public Integer getId_curso() {
        return id_curso;
    }

    public void setId_curso(Integer id_curso) {
        this.id_curso = id_curso;
    }

    public Integer getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(Integer id_pregunta) {
        this.id_pregunta = id_pregunta;
    }
}
