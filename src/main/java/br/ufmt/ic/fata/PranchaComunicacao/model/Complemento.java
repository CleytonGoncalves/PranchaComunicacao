package br.ufmt.ic.fata.PranchaComunicacao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Complemento extends Palavra {
    
    @NotBlank
    private String sexo;
    
    @ManyToMany(mappedBy = "complementos")
    @JsonBackReference
    private List<Paciente> pacientes;
    
}
