package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by Michael Estrada on 10/21/17.
 */

public class Curso implements Serializable {


    private Integer id_curso;
    private String desc_curso;
    private Integer id_area;

    public Curso(){super();}

    public Curso(Integer id_curso, String desc_curso, Integer id_area) {
        this.id_curso = id_curso;
        this.desc_curso = desc_curso;
        this.id_area = id_area;
    }

    public Integer getId_curso() {
        return id_curso;
    }

    public void setId_curso(Integer id_curso) {
        this.id_curso = id_curso;
    }

    public String getDesc_curso() {
        return desc_curso;
    }

    public void setDesc_curso(String desc_curso) {
        this.desc_curso = desc_curso;
    }

    public Integer getId_area() {
        return id_area;
    }

    public void setId_area(Integer id_area) {
        this.id_area = id_area;
    }
}
