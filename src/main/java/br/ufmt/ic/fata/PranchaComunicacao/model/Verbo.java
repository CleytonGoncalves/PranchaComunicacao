package br.ufmt.ic.fata.PranchaComunicacao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

import static br.ufmt.ic.fata.PranchaComunicacao.model.Pronome.EU;
import static br.ufmt.ic.fata.PranchaComunicacao.model.Pronome.NOS;
import static br.ufmt.ic.fata.PranchaComunicacao.model.Pronome.VOCE;

@Entity
@Getter @Setter @NoArgsConstructor
public class Verbo extends Palavra {
    
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
    
    @SuppressWarnings("Duplicates")
    public String getVerboConjugado(Pronome pronome, TempoVerbal tempoVerbal) {
        if (pronome == EU) {
            switch (tempoVerbal) {
                case PASSADO:
                    return passadoEu;
                case PRESENTE:
                    return presenteEu;
                case FUTURO:
                    return futuroEu;
            }
        } else if (pronome == NOS) {
            switch (tempoVerbal) {
                case PASSADO:
                    return passadoNos;
                case PRESENTE:
                    return presenteNos;
                case FUTURO:
                    return futuroNos;
            }
        } else if (pronome == VOCE) {
            switch (tempoVerbal) {
                case PASSADO:
                    return passadoVoce;
                case PRESENTE:
                    return presenteVoce;
                case FUTURO:
                    return futuroVoce;
            }
        }
    
        throw new IllegalArgumentException(String.format("Conjunto de Pronome e Tempo Verbal desconhecido: %s - %s.",
                pronome, tempoVerbal));
    }
    
}
