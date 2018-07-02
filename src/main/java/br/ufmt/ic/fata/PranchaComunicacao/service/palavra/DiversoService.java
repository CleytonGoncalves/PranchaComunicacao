package br.ufmt.ic.fata.PranchaComunicacao.service.palavra;

import br.ufmt.ic.fata.PranchaComunicacao.model.Diverso;
import br.ufmt.ic.fata.PranchaComunicacao.repository.DiversoRepository;
import br.ufmt.ic.fata.PranchaComunicacao.repository.PalavraRepository;
import br.ufmt.ic.fata.PranchaComunicacao.service.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento.ArmazenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiversoService extends PalavraService<Diverso> {
    
    private final DiversoRepository repository;
    
    @Autowired
    public DiversoService(DiversoRepository repo, ArmazenamentoService as, ValidadorImagem vi) {
        super(as, vi);
        this.repository = repo;
    }
    
    @Override
    protected PalavraRepository<Diverso> getRepository() {
        return repository;
    }
    
}
