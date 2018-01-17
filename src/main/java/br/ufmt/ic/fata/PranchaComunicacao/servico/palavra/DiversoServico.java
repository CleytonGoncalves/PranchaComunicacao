package br.ufmt.ic.fata.PranchaComunicacao.servico.palavra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Diverso;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.DiversoRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PalavraRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.servico.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;

@Service
public class DiversoServico extends PalavraServico<Diverso> {
    
    private final DiversoRepositorio repositorio;
    
    @Autowired
    public DiversoServico(DiversoRepositorio repo, ArmazenamentoServico as, ValidadorImagem vi) {
        super(as, vi);
        this.repositorio = repo;
    }
    
    @Override
    protected PalavraRepositorio<Diverso> getRepositorio() {
        return repositorio;
    }
    
}
