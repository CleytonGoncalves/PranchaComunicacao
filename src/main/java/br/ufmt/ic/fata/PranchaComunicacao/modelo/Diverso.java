package br.ufmt.ic.fata.PranchaComunicacao.modelo;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Diverso extends Palavra {
    
    @NotBlank
    private String tipo;
    
    @ManyToMany(mappedBy = "diversos")
    private List<Paciente> pacientes;
    
}
