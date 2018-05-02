package br.ufmt.ic.fata.PranchaComunicacao.service.paciente;

import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.repository.PacienteRepository;
import br.ufmt.ic.fata.PranchaComunicacao.repository.base.BaseRepository;
import br.ufmt.ic.fata.PranchaComunicacao.service.base.BaseCrudService;
import org.springframework.stereotype.Service;

@Service
public class PacienteService extends BaseCrudService<Paciente> {
    
    private final PacienteRepository repository;
    
    public PacienteService(PacienteRepository repo) {
        this.repository = repo;
    }
    
    @Override
    protected BaseRepository<Paciente> getRepository() {
        return repository;
    }
    
}
