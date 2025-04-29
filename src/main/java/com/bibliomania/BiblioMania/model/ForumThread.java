package com.bibliomania.BiblioMania.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "forum_threads")
public class ForumThread  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    
    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private GrupoLectura grupo; 
    
    @OneToMany(mappedBy = "forumThread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    // Getters y Setters
    
	public Long getIdThread() {
		return id;
	}

	public void setIdThread(Long idThread) {
		this.id = idThread;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public GrupoLectura getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoLectura grupo) {
		this.grupo = grupo;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}  
    
  
    
    
    
}
