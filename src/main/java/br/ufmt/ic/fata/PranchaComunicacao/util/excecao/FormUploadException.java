package br.ufmt.ic.fata.PranchaComunicacao.util.excecao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Formulário inválido")
public class FormUploadException extends RuntimeException {
    
    public FormUploadException(String mensagem) {
        super(mensagem);
    }
    
    public FormUploadException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    
}
