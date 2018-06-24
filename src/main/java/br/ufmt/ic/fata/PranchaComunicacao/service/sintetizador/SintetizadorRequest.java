package br.ufmt.ic.fata.PranchaComunicacao.service.sintetizador;

import br.ufmt.ic.fata.PranchaComunicacao.model.TempoVerbal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @NoArgsConstructor @ToString
public class SintetizadorRequest {
    private List<String> palavrasIds;
    private TempoVerbal tempoVerbal;
}
