package br.ufmt.ic.fata.PranchaComunicacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"application.properties", "file:./secrets.properties"})
public class PranchaComunicacaoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PranchaComunicacaoApplication.class, args);
    }
    
}
