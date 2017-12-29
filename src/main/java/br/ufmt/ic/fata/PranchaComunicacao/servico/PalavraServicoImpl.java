package br.ufmt.ic.fata.PranchaComunicacao.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PalavraRepositorio;

@SuppressWarnings("unchecked")
@Service("palavraServico")
public class PalavraServicoImpl<P extends Palavra> implements PalavraServico<P> {
    
    @Autowired
    PalavraRepositorio palavraRepositorio;
    
    public void salvar(Palavra palavra) {
        palavraRepositorio.save(palavra);
    }
    
    public void remover(Palavra palavra) {
        palavraRepositorio.delete(palavra);
    }
    
    public List<P> buscarPorPalavra(String palavra) {
        return (List<P>) palavraRepositorio.findByPalavra(palavra);
    }
    
    public Optional<P> buscarPorId(Long id) {
        return Optional.of((P) palavraRepositorio.findOne(id));
    }
    
    public List<P> buscarTodas() {
        return (List<P>) palavraRepositorio.findAll();
    }
    
}
