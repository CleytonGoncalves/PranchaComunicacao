package br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento;

public class ArmazenamentoException extends RuntimeException {
    
    public ArmazenamentoException(String mensagem) {
        super(mensagem);
    }
    
    public ArmazenamentoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
}
