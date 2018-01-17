package br.ufmt.ic.fata.PranchaComunicacao.servico.palavra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Verbo;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PalavraRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.VerboRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.servico.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;

@Service
public class VerboServico extends PalavraServico<Verbo> {
    
    private final VerboRepositorio repositorio;
    
    @Autowired
    public VerboServico(VerboRepositorio repo, ArmazenamentoServico as, ValidadorImagem vi) {
        super(as, vi);
    
        this.repositorio = repo;
    }
    
    @Override
    protected PalavraRepositorio<Verbo> getRepositorio() {
        return repositorio;
    }
    
}
