package br.ufmt.ic.fata.PranchaComunicacao.controlador.web.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.servico.paciente.PacienteServico;

@Controller
@RequestMapping("/pastaPaciente")
public class PacienteControlador {
    
    //TODO: Criar Cadastro e Edição de Pacientes
    
    private static final String PAGINA_INICIAL = "pastaPaciente";
    
    private final PacienteServico servico;
    
    @Autowired
    public PacienteControlador(PacienteServico servico) {
        this.servico = servico;
    }
    
    @ModelAttribute("listaPaciente")
    public List<Paciente> getListaPaciente() {
        return servico.buscarTodos();
    }
    
    @GetMapping
    public String getPaginaInicial() {
        return PAGINA_INICIAL;
    }
    
}
