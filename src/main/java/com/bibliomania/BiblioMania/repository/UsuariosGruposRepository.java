package com.bibliomania.BiblioMania.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bibliomania.BiblioMania.model.UsuariosGrupos;
import com.bibliomania.BiblioMania.model.UsuariosGruposId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosGruposRepository extends JpaRepository<UsuariosGrupos, UsuariosGruposId> {
    void deleteById(UsuariosGruposId id);
    boolean existsById(UsuariosGruposId id);

    @Query("SELECT COUNT(u) FROM UsuariosGrupos u WHERE u.grupo.id = :grupoId")
    long countUsersInGroup(@Param("grupoId") Long grupoId);
    
    @Query("SELECT COUNT(u) > 0 FROM UsuariosGrupos u WHERE u.usuario.id = :userId AND u.grupo.id = :grupoId")
    boolean isUserInGroup(@Param("userId") Long userId, @Param("grupoId") Long grupoId);
    
    @Query("SELECT u.usuario.id FROM UsuariosGrupos u WHERE u.grupo.id = :grupoId")
    List<Long> findUserIdsByGrupoId(@Param("grupoId") Long grupoId);

}
