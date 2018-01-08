package br.ufmt.ic.fata.PranchaComunicacao.servico.palavra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Complemento;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.ComplementoRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.servico.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;

@Service
public class ComplementoServico extends PalavraServico<Complemento> {
    
    @Autowired
    public ComplementoServico(ComplementoRepositorio repo, ArmazenamentoServico as,
                              ValidadorImagem vi) {
        super(repo, as, vi);
    }
    
}
