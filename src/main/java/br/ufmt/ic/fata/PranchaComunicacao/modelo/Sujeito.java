package br.ufmt.ic.fata.PranchaComunicacao.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Sujeito extends Palavra {
    
    @NotBlank
    private String sexo;
    
    @NotBlank
    private String pronome;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "sujeitos")
    private List<Paciente> pacientes;
    
}
