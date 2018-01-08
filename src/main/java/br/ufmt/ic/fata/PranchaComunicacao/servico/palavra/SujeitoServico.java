package br.ufmt.ic.fata.PranchaComunicacao.servico.palavra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.SujeitoRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.servico.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;

@Service
public class SujeitoServico extends PalavraServico<Sujeito> {
    
    @Autowired
    public SujeitoServico(SujeitoRepositorio repo, ArmazenamentoServico as, ValidadorImagem vi) {
        super(repo, as, vi);
    }
    
}
