package br.ufmt.ic.fata.PranchaComunicacao.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Arquivo inválido")
public class ArmazenamentoException extends RuntimeException {
    
    public ArmazenamentoException(String mensagem) {
        super(mensagem);
    }
    
    public ArmazenamentoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
}
