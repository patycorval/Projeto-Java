package com.example.reservaSala.model;
import jakarta.persistence.*;
import java.util.List;

import com.example.reservaSala.model.enums.Recurso;
import com.example.reservaSala.model.enums.TipoSala;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero; // NUMERO DA SALA
    private Integer capacidade; // QT ALUNO
    private String localizacao; // ANDAR
    private TipoSala tipo;   // Ex: Laboratório, Sala comum, Auditório
    private boolean ativa = true; // SE ESTA RESERVADA OU NAO 

    @ElementCollection(targetClass = Recurso.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "sala_recursos", joinColumns = @JoinColumn(name = "sala_id"))
    @Column(name = "recurso")
    private List<Recurso> recursos; // LISTA DE ENUMS DE RECURSOS POSSIVEIS NA SALA 

    public Sala() {
    }

    public Sala(String numero, Integer capacidade, String localizacao, TipoSala tipo, boolean ativa, List<Recurso> recursos) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.tipo = tipo;
        this.ativa = ativa;
        this.recursos = recursos;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }

    public List<Recurso> getRecursos() { return recursos; }
    public void setRecursos(List<Recurso> recursos) { this.recursos = recursos; }

    public TipoSala getTipo() { return tipo; }
    public void setTipo(TipoSala tipo) { this.tipo = tipo; }
}
