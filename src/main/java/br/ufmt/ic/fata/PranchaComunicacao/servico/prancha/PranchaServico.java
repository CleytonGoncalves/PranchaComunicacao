package br.ufmt.ic.fata.PranchaComunicacao.servico.prancha;

import com.sun.istack.internal.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.servico.paciente.PacienteServico;

@Service
public class PranchaServico {
    
    private final PacienteServico pacienteServico;
    
    @Autowired
    public PranchaServico(PacienteServico ps) {
        this.pacienteServico = ps;
    }
    
    @Nullable
    public Paciente getPacientePorId(Long id) {
        return pacienteServico.buscarPorId(id);
    }
    
}
