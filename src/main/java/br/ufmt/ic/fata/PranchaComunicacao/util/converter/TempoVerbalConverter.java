package br.ufmt.ic.fata.PranchaComunicacao.util.converter;

import br.ufmt.ic.fata.PranchaComunicacao.model.TempoVerbal;

import java.beans.PropertyEditorSupport;

public class TempoVerbalConverter extends PropertyEditorSupport {
    
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(TempoVerbal.fromValue(text));
    }
    
}


