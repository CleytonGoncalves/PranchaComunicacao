package br.ufmt.ic.fata.PranchaComunicacao.model;

public enum TempoVerbal {
    PASSADO("Passado"), PRESENTE("Presente"), FUTURO("Futuro");
    
    private String value;
    
    TempoVerbal(String value) {
        this.value = value;
    }
    
    public static TempoVerbal fromValue(String value) {
        for (TempoVerbal tempo : values()) {
            if (tempo.value.equalsIgnoreCase(value)) {
                return tempo;
            }
        }
        throw new IllegalArgumentException("Valor de enum desconhecido: " + value + ".");
    }
    
}
