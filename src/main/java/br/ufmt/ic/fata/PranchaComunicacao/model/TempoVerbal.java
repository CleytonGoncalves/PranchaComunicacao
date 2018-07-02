package br.ufmt.ic.fata.PranchaComunicacao.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TempoVerbal {
    PASSADO("Passado"), PRESENTE("Presente"), FUTURO("Futuro");
    
    private String value;
    
    TempoVerbal(String value) {
        this.value = value;
    }
    
    @JsonCreator
    public static TempoVerbal fromValue(String value) {
        for (TempoVerbal tempo : values()) {
            if (tempo.value.equalsIgnoreCase(value)) {
                return tempo;
            }
        }
        throw new IllegalArgumentException("Valor de enum desconhecido: " + value + ".");
    }
    
    @JsonValue
    public String toValue() {
        return this.toString();
    }
    
}
