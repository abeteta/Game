package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by alex on 11/18/17.
 */

public class VersusCursos implements Serializable {

    private Integer id_versus;
    private Integer id_curso;

    public VersusCursos (){super();}

    public VersusCursos(Integer id_versus, Integer id_curso) {
        this.id_versus = id_versus;
        this.id_curso = id_curso;
    }

    public Integer getId_versus() {
        return id_versus;
    }

    public void setId_versus(Integer id_versus) {
        this.id_versus = id_versus;
    }

    public Integer getId_curso() {
        return id_curso;
    }

    public void setId_curso(Integer id_curso) {
        this.id_curso = id_curso;
    }
}
