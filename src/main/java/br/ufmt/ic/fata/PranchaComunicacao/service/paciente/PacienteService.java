package br.ufmt.ic.fata.PranchaComunicacao.service.paciente;

import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.repository.PacienteRepository;
import br.ufmt.ic.fata.PranchaComunicacao.repository.base.BaseRepository;
import br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento.ArmazenamentoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.base.BaseCrudService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteService extends BaseCrudService<Paciente> {
    
    private final PacienteRepository repository;
    private ArmazenamentoService armazenamentoService;
    
    public PacienteService(PacienteRepository repo, ArmazenamentoService as) {
        this.repository = repo;
        this.armazenamentoService = as;
    }
    
    public String salvarImagem(MultipartFile imagem, Errors erros) {
        return armazenamentoService.salvarImagem(imagem, erros);
    }
    
    @Override
    protected BaseRepository<Paciente> getRepository() {
        return repository;
    }
    
}
