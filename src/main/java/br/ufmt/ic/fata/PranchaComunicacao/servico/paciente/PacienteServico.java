package br.ufmt.ic.fata.PranchaComunicacao.servico.paciente;

import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PacienteRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.base.RepositorioBase;
import br.ufmt.ic.fata.PranchaComunicacao.servico.base.ServicoCrudBase;

@Service
public class PacienteServico extends ServicoCrudBase<Paciente> {
    
    private final PacienteRepositorio repositorio;
    
    public PacienteServico(PacienteRepositorio repo) {
        this.repositorio = repo;
    }
    
    @Override
    protected RepositorioBase<Paciente> getRepositorio() {
        return repositorio;
    }
    
}
