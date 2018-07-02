package br.ufmt.ic.fata.PranchaComunicacao.service.palavra;

import br.ufmt.ic.fata.PranchaComunicacao.model.Complemento;
import br.ufmt.ic.fata.PranchaComunicacao.repository.ComplementoRepository;
import br.ufmt.ic.fata.PranchaComunicacao.repository.PalavraRepository;
import br.ufmt.ic.fata.PranchaComunicacao.service.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento.ArmazenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplementoService extends PalavraService<Complemento> {
    
    private final ComplementoRepository repository;
    
    @Autowired
    public ComplementoService(ComplementoRepository repo, ArmazenamentoService as,
                              ValidadorImagem vi) {
        super(as, vi);
        repository = repo;
    }
    
    @Override
    protected PalavraRepository<Complemento> getRepository() {
        return repository;
    }
    
}
