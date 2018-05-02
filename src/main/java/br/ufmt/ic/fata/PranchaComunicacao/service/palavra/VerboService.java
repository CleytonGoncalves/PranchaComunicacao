package br.ufmt.ic.fata.PranchaComunicacao.service.palavra;

import br.ufmt.ic.fata.PranchaComunicacao.model.Verbo;
import br.ufmt.ic.fata.PranchaComunicacao.repository.PalavraRepository;
import br.ufmt.ic.fata.PranchaComunicacao.repository.VerboRepository;
import br.ufmt.ic.fata.PranchaComunicacao.service.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento.ArmazenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerboService extends PalavraService<Verbo> {
    
    private final VerboRepository repository;
    
    @Autowired
    public VerboService(VerboRepository repo, ArmazenamentoService as, ValidadorImagem vi) {
        super(as, vi);
    
        this.repository = repo;
    }
    
    @Override
    protected PalavraRepository<Verbo> getRepository() {
        return repository;
    }
    
}
