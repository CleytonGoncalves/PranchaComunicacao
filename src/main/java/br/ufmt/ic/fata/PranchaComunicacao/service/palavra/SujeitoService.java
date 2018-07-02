package br.ufmt.ic.fata.PranchaComunicacao.service.palavra;

import br.ufmt.ic.fata.PranchaComunicacao.model.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.repository.PalavraRepository;
import br.ufmt.ic.fata.PranchaComunicacao.repository.SujeitoRepository;
import br.ufmt.ic.fata.PranchaComunicacao.service.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento.ArmazenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SujeitoService extends PalavraService<Sujeito> {
    
    private final SujeitoRepository repository;
    
    @Autowired
    public SujeitoService(SujeitoRepository repo, ArmazenamentoService as, ValidadorImagem vi) {
        super(as, vi);
        repository = repo;
    }
    
    @Override
    protected PalavraRepository<Sujeito> getRepository() {
        return repository;
    }
}
