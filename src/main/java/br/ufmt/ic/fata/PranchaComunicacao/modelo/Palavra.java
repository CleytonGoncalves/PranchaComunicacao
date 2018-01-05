package br.ufmt.ic.fata.PranchaComunicacao.modelo;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_especifico") //Coluna que identifica a entidade-filha
@Getter @Setter @NoArgsConstructor
public class Palavra extends EntidadeBase {
    
    @NotBlank
    private String palavra;
    
    // Não deve conter anotação NotBlank devido a imagem ser validada separadamente
    private String url;
    
}

