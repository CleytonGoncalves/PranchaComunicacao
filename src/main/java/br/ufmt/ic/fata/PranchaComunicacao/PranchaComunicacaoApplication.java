package br.ufmt.ic.fata.PranchaComunicacao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.HashSet;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoDiscoServico;
import br.ufmt.ic.fata.PranchaComunicacao.servico.paciente.PacienteServico;
import br.ufmt.ic.fata.PranchaComunicacao.servico.palavra.ComplementoServico;
import br.ufmt.ic.fata.PranchaComunicacao.servico.palavra.DiversoServico;
import br.ufmt.ic.fata.PranchaComunicacao.servico.palavra.SujeitoServico;
import br.ufmt.ic.fata.PranchaComunicacao.servico.palavra.VerboServico;

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
    CommandLineRunner init(ArmazenamentoDiscoServico armazenamento, SujeitoServico ss,
                           VerboServico vs, PacienteServico ps, ComplementoServico cs,
                           DiversoServico ds) {
        return (args) -> {
    
            for (Paciente p : ps.buscarTodos()) {
                p.setSujeitos(new HashSet<>(ss.buscarTodos()));
                p.setVerbos(new HashSet<>(vs.buscarTodos()));
                p.setComplementos(new HashSet<>(cs.buscarTodos()));
                p.setDiversos(new HashSet<>(ds.buscarTodos()));
        
                ps.salvar(p);
            }
            //TODO: Remover entradas de teste acima
            
            armazenamento.init();
        };
    }
    
}
