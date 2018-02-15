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
public class Verbo extends Palavra {
    
    //TODO: Criar ENUM com Eu/Você/Nós e associar o tempo ao enum em um mapa
    @NotBlank
    private String presenteEu;
    
    @NotBlank
    private String presenteVoce;
    
    @NotBlank
    private String presenteNos;
    
    @NotBlank
    private String passadoEu;
    
    @NotBlank
    private String passadoVoce;
    
    @NotBlank
    private String passadoNos;
    
    @NotBlank
    private String futuroEu;
    
    @NotBlank
    private String futuroVoce;
    
    @NotBlank
    private String futuroNos;
    
    @ManyToMany(mappedBy = "verbos")
    @JsonBackReference
    private List<Paciente> pacientes;
    
}
