package br.ufmt.ic.fata.PranchaComunicacao.servico;

import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PacienteRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.base.RepositorioBase;
import br.ufmt.ic.fata.PranchaComunicacao.servico.base.ServicoBase;

@Service
public class PacienteServico extends ServicoBase<Paciente> {
    
    private final PacienteRepositorio repositorio;
    
    public PacienteServico(PacienteRepositorio repo) {
        this.repositorio = repo;
    }
    
    @Override
    protected RepositorioBase<Paciente> getRepositorio() {
        return repositorio;
    }
    
}
