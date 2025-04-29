package com.bibliomania.BiblioMania.service;

import com.bibliomania.BiblioMania.dto.GrupoLecturaDTO;
import com.bibliomania.BiblioMania.model.*;
import com.bibliomania.BiblioMania.repository.GrupoLecturaRepository;
import com.bibliomania.BiblioMania.repository.UsuariosGruposRepository;
import com.bibliomania.BiblioMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrupoLecturaService {

    @Autowired
    private GrupoLecturaRepository grupoLecturaRepository;

    @Autowired
    private UsuariosGruposRepository usuariosGruposRepository;

    @Autowired
    private UserRepository userRepository;

  //  public List<GrupoLectura> getAllGrupos() {
  //      return grupoLecturaRepository.findAll();
  //  }

    public GrupoLectura getGrupoById(Long id) {
        return grupoLecturaRepository.findById(id).orElse(null);
    }

    public GrupoLectura createGrupo(GrupoLectura grupoLectura) {
        return grupoLecturaRepository.save(grupoLectura);
    }

    public void deleteGrupo(Long id) {
        grupoLecturaRepository.deleteById(id);
    }

    public boolean addUserToGroup(Long userId, Long groupId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<GrupoLectura> group = grupoLecturaRepository.findById(groupId);

        if (user.isPresent() && group.isPresent()) {
            UsuariosGruposId id = new UsuariosGruposId(userId, groupId);
            if (!usuariosGruposRepository.existsById(id)) {
                UsuariosGrupos userGroup = new UsuariosGrupos();
                userGroup.setId(id);
                userGroup.setUsuario(user.get());
                userGroup.setGrupo(group.get());
                usuariosGruposRepository.save(userGroup);
                return true;
            }
        }
        return false;
    }

    public boolean removeUserFromGroup(Long userId, Long groupId) {
        UsuariosGruposId id = new UsuariosGruposId(userId, groupId);
        if (usuariosGruposRepository.existsById(id)) {
            usuariosGruposRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public GrupoLecturaService(GrupoLecturaRepository grupoLecturaRepository, UsuariosGruposRepository usuariosGruposRepository) {
        this.grupoLecturaRepository = grupoLecturaRepository;
        this.usuariosGruposRepository = usuariosGruposRepository;
    }

    public List<GrupoLecturaDTO> getAllGrupos() {
        List<GrupoLectura> grupos = grupoLecturaRepository.findAll();
        return grupos.stream()
            .map(grupo -> new GrupoLecturaDTO(grupo, usuariosGruposRepository.countUsersInGroup(grupo.getidGrupo())))
            .collect(Collectors.toList());
    }
    
    public boolean isUserMember(Long userId, Long grupoId) {
        return usuariosGruposRepository.isUserInGroup(userId, grupoId);
    }
}
