package br.ufmt.ic.fata.PranchaComunicacao.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ValidadorImagem implements Validator {
    
    private static final String[] tiposValidos = {"image/jpg", "image/jpeg", "image/png",
                                                  "image/gif"};
    
    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile arquivo = (MultipartFile) target;
        
        // Mensagens definidas no messages.properties
        if (arquivo.isEmpty() || arquivo.getSize() == 0) {
            errors.rejectValue("imagem", "arquivo.vazio");
        } else if (! isTipoValido(arquivo)) {
            errors.rejectValue("imagem", "arquivo.tipoimagem");
        }
    }
    
    private boolean isTipoValido(MultipartFile arquivo) {
        for (String tipo : tiposValidos) {
            if (arquivo.getContentType().equalsIgnoreCase(tipo)) {
                return true;
            }
        }
        
        return false;
    }
    
}
