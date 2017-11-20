package com.garmadell.videoplayer.view.bean;

import java.io.Serializable;

/**
 * Created by Michael Estrada on 11/19/17.
 */

public class EsperandoOponente implements Serializable {
    private Integer id_jugador_secundario;
    private Integer estado_versus;

    public EsperandoOponente () {super();}

    public EsperandoOponente(Integer id_jugador_secundario, Integer estado_versus) {
        this.id_jugador_secundario = id_jugador_secundario;
        this.estado_versus = estado_versus;
    }

    public Integer getId_jugador_secundario() {
        return id_jugador_secundario;
    }

    public void setId_jugador_secundario(Integer id_jugador_secundario) {
        this.id_jugador_secundario = id_jugador_secundario;
    }

    public Integer getEstado_versus() {
        return estado_versus;
    }

    public void setEstado_versus(Integer estado_versus) {
        this.estado_versus = estado_versus;
    }
}
