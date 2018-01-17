package br.ufmt.ic.fata.PranchaComunicacao.servico.palavra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Complemento;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.ComplementoRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PalavraRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.servico.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;

@Service
public class ComplementoServico extends PalavraServico<Complemento> {
    
    private final ComplementoRepositorio repositorio;
    
    @Autowired
    public ComplementoServico(ComplementoRepositorio repo, ArmazenamentoServico as,
                              ValidadorImagem vi) {
        super(as, vi);
        repositorio = repo;
    }
    
    @Override
    protected PalavraRepositorio<Complemento> getRepositorio() {
        return repositorio;
    }
    
}
