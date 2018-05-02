package br.ufmt.ic.fata.PranchaComunicacao.service.sintetizador;

import br.ufmt.ic.fata.PranchaComunicacao.model.Complemento;
import br.ufmt.ic.fata.PranchaComunicacao.model.Diverso;
import br.ufmt.ic.fata.PranchaComunicacao.model.Pronome;
import br.ufmt.ic.fata.PranchaComunicacao.model.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.model.TempoVerbal;
import br.ufmt.ic.fata.PranchaComunicacao.model.Verbo;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.ComplementoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.DiversoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.SujeitoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.VerboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.ufmt.ic.fata.PranchaComunicacao.model.Pronome.EU;

@Service
public class SintetizadorService {
    
    private final SujeitoService sujeitoService;
    private final VerboService verboService;
    private final ComplementoService complementoService;
    private final DiversoService diversoService;
    
    @Autowired
    private SintetizadorService(SujeitoService ss, VerboService vs, ComplementoService cs, DiversoService ds) {
        this.sujeitoService = ss;
        this.verboService = vs;
        this.complementoService = cs;
        this.diversoService = ds;
    }
    
    public String getTextoFromListaPalavrasIds(List<String> listaPalavra, TempoVerbal tempoVerbal) {
        StringBuilder texto = new StringBuilder();
        
        Pronome pronome = EU; // Padrão
        
        for (String palavraId : listaPalavra) {
            String[] palavraArr = palavraId.split("-"); // Formato: "sujeito-3"
            String tipo = palavraArr[0];
            Long id = Long.parseLong(palavraArr[1]);
            
            switch (tipo) {
                case "sujeito":
                    Sujeito sujeito = sujeitoService.buscarPorId(id);
                    pronome = sujeito.getPronome();
                    texto.append(" ").append(sujeito.getPalavra());
                    break;
                case "verbo":
                    Verbo verbo = verboService.buscarPorId(id);
                    texto.append(" ").append(verbo.getVerboConjugado(pronome, tempoVerbal));
                    break;
                case "complemento":
                    Complemento complemento = complementoService.buscarPorId(id);
                    texto.append(" ").append(complemento.getPalavra());
                case "diverso":
                    Diverso diverso = diversoService.buscarPorId(id);
                    texto.append(" ").append(diverso.getPalavra());
            }
        }
        
        return texto.toString();
    }
    
}
