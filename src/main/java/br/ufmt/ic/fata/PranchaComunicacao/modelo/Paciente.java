package br.ufmt.ic.fata.PranchaComunicacao.modelo;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.base.EntidadeBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Paciente extends EntidadeBase {
    
    @NotBlank
    private String nome;
    
    private String dataNascimento;
    
    private String sexo;
    
    private String imagemUrl;
    
    @Column(nullable = false)
    @ColumnDefault("50")
    private int velocidadeVoz;
    
    @Column(nullable = false)
    @ColumnDefault("50")
    private int velocidadeSelecao;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "paciente_pasta_sujeito",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "sujeito_id"))
    private Set<Sujeito> sujeitos;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "paciente_pasta_verbo",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "verbo_id"))
    private Set<Verbo> verbos;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "paciente_pasta_complemento",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "complemento_id"))
    private Set<Complemento> complementos;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "paciente_pasta_diverso",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "diverso_id"))
    private Set<Diverso> diversos;
    
    
    public Paciente() {
        this.complementos = new HashSet<>();
        this.verbos = new HashSet<>();
        this.sujeitos = new HashSet<>();
        this.diversos = new HashSet<>();
    }
    
}
