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
public class Verbo extends Palavra {
    
    //TODO: Refatorar esses campos da pessoa gramatical com tempo
    @NotBlank
    private String presentePrimeiraPessoa;
    
    @NotBlank
    private String presenteSegundaPessoa;
    
    @NotBlank
    private String presenteTerceiraPessoa;
    
    @NotBlank
    private String passadoPrimeiraPessoa;
    
    @NotBlank
    private String passadoSegundaPessoa;
    
    @NotBlank
    private String passadoTerceiraPessoa;
    
    @NotBlank
    private String futuroPrimeiraPessoa;
    
    @NotBlank
    private String futuroSegundaPessoa;
    
    @NotBlank
    private String futuroTerceiraPessoa;
    
    @ManyToMany(mappedBy = "verbos")
    private List<Paciente> pacientes;
    
}
