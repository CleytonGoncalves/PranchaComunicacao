package br.ufmt.ic.fata.PranchaComunicacao.model;

public enum Pronome {
    EU("Eu"), VOCE("Voce"), NOS("Nos");
    
    private String value;
    
    Pronome(String value) {
        this.value = value;
    }
    
    public static Pronome fromValue(String value) {
        for (Pronome pronome : values()) {
            if (pronome.value.equalsIgnoreCase(value)) {
                return pronome;
            }
        }
        throw new IllegalArgumentException("Valor de enum desconhecido: " + value + ".");
    }
    
}
