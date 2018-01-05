package br.ufmt.ic.fata.PranchaComunicacao.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Palavra não encontrada")
public class PalavraNaoEncontradaException extends RuntimeException {
    
    public PalavraNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public PalavraNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
}
