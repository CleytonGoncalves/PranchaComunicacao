package br.ufmt.ic.fata.PranchaComunicacao.model;

import br.ufmt.ic.fata.PranchaComunicacao.model.base.EntidadeBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public abstract class Palavra extends EntidadeBase {
    
    @NotBlank
    private String palavra;
    
    // Não deve conter anotação NotBlank devido a imagem ser validada separadamente
    private String url;
    
}

