package br.ufmt.ic.fata.PranchaComunicacao.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Complemento extends Palavra {
    
    @NotBlank
    private String sexo;
    
    @ManyToMany(mappedBy = "complementos")
    @JsonBackReference
    private List<Paciente> pacientes;
    
}
