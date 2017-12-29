package br.ufmt.ic.fata.PranchaComunicacao.servico;

import java.util.List;
import java.util.Optional;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;

public interface PalavraServico<P extends Palavra> {
    
    void salvar(Palavra palavra);
    
    void remover(Palavra palavra);
    
    List<P> buscarPorPalavra(String palavra);
    
    Optional<P> buscarPorId(Long id);
    
    List<P> buscarTodas();
    
}
