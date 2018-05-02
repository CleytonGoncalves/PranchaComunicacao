package br.ufmt.ic.fata.PranchaComunicacao.service.prancha;


import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.service.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PranchaService {
    
    private final PacienteService pacienteService;
    
    @Autowired
    public PranchaService(PacienteService ps) {
        this.pacienteService = ps;
    }
    
    public Paciente getPacientePorId(Long id) {
        return pacienteService.buscarPorId(id);
    }
    
}
