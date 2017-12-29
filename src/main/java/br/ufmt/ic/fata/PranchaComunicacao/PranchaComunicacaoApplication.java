package br.ufmt.ic.fata.PranchaComunicacao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoDiscoServico;

@SpringBootApplication
@PropertySource(value = {"application.properties", "segredos.application.properties"})
public class PranchaComunicacaoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PranchaComunicacaoApplication.class, args);
    }
    
    /**
     * Inicializa o diretório de armazenamento de arquivos - Roda automático
     */
    @Bean
    CommandLineRunner init(ArmazenamentoDiscoServico armazenamento) {
        return (args) -> {
            armazenamento.init();
        };
    }
    
}
