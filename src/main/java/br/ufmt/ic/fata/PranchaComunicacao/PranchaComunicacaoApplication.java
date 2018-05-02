package br.ufmt.ic.fata.PranchaComunicacao;

import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento.ArmazenamentoDiscoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.paciente.PacienteService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.ComplementoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.DiversoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.SujeitoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.VerboService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.HashSet;

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
    CommandLineRunner init(ArmazenamentoDiscoService armazenamento, SujeitoService ss,
                           VerboService vs, PacienteService ps, ComplementoService cs,
                           DiversoService ds) {
        return (args) -> {
    
            for (Paciente p : ps.buscarTodos()) {
                p.setSujeitos(new HashSet<>(ss.buscarTodos()));
                p.setVerbos(new HashSet<>(vs.buscarTodos()));
                p.setComplementos(new HashSet<>(cs.buscarTodos()));
                p.setDiversos(new HashSet<>(ds.buscarTodos()));
        
                ps.salvar(p);
            }
            //TODO: Remover entradas de teste acima
            
        };
    }
    
}
