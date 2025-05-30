package com.bibliomania.BiblioMania.dto;

import com.bibliomania.BiblioMania.model.GrupoLectura;

public class GrupoLecturaDTO {
    private Long idGrupo;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private Long idCreador;
    private String nombreCreador;
    private long miembros;

    public GrupoLecturaDTO(GrupoLectura grupo, long miembros) {
        this.idGrupo = grupo.getidGrupo();
        this.nombre = grupo.getNombre();
        this.descripcion = grupo.getDescripcion();
        this.activo = grupo.getActivo();
        this.miembros = miembros;

        if(grupo.getCreador() != null ) {
                this.idCreador = grupo.getCreador().getId();
                this.nombreCreador = grupo.getCreador().getName();
        }

    }

    // Getters
    public Long getIdGrupo() { return idGrupo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public boolean isActivo() { return activo; }
    public long getMiembros() { return miembros; }
    public Long getIdCreador() { return idCreador; }
    public String getnombreCreador() { return nombreCreador; }
}
