package br.ufmt.ic.fata.PranchaComunicacao.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    
    private LocalDate dataNascimento;
    
    private String sexo;
    
    private String imagemUrl;
    
    /* Inicializados com valor padrão */
    
    private int velocidadeVoz = 50;
    
    private int velocidadeSelecao = 50;
    
    /* Por que Set? https://thoughts-on-java.org/association-mappings-bag-list-set */
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_pasta_sujeito",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "sujeito_id"))
    @JsonManagedReference
    private Set<Sujeito> sujeitos;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_pasta_verbo",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "verbo_id"))
    @JsonManagedReference
    private Set<Verbo> verbos;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_pasta_complemento",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "complemento_id"))
    @JsonManagedReference
    private Set<Complemento> complementos;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_pasta_diverso",
               joinColumns = @JoinColumn(name = "paciente_id"),
               inverseJoinColumns = @JoinColumn(name = "diverso_id"))
    @JsonManagedReference
    private Set<Diverso> diversos;
    
    
    public Paciente() {
        this.complementos = new HashSet<>();
        this.verbos = new HashSet<>();
        this.sujeitos = new HashSet<>();
        this.diversos = new HashSet<>();
    }
    
}
