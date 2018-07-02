package br.ufmt.ic.fata.PranchaComunicacao.util.converter;

import br.ufmt.ic.fata.PranchaComunicacao.model.Pronome;

import java.beans.PropertyEditorSupport;

public class PronomeConverter extends PropertyEditorSupport {
    
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(Pronome.fromValue(text));
    }
    
}


