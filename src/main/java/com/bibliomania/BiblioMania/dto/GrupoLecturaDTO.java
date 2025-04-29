package com.bibliomania.BiblioMania.dto;

import com.bibliomania.BiblioMania.model.GrupoLectura;

public class GrupoLecturaDTO {
    private Long idGrupo;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private long miembros; // ✅ Agregamos el número de miembros

    public GrupoLecturaDTO(GrupoLectura grupo, long miembros) {
        this.idGrupo = grupo.getidGrupo();
        this.nombre = grupo.getNombre();
        this.descripcion = grupo.getDescripcion();
        this.activo = grupo.getActivo();
        this.miembros = miembros;
    }

    // Getters
    public Long getIdGrupo() { return idGrupo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public boolean isActivo() { return activo; }
    public long getMiembros() { return miembros; }
}
