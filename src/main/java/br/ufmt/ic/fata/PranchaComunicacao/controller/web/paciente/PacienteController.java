package br.ufmt.ic.fata.PranchaComunicacao.controller.web.paciente;

import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.service.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pastaPaciente")
public class PacienteController {
    
    //TODO: Criar Cadastro e Edição de Pacientes
    
    private static final String PAGINA_INICIAL = "pastaPaciente";
    private static final String PAGINA_PRANCHA = "pranchaComunicacao";
    
    private final PacienteService service;
    
    @Autowired
    public PacienteController(PacienteService service) {
        this.service = service;
    }
    
    @ModelAttribute("listaPaciente")
    public List<Paciente> getListaPaciente() {
        return service.buscarTodos();
    }
    
    @GetMapping
    public String getPaginaInicial() {
        return PAGINA_INICIAL;
    }
    
    @PostMapping("/selecionarPaciente/{id}")
    public String selecionarPaciente(@PathVariable(name = "id") long pacienteId) {
        return "redirect:/" + PAGINA_PRANCHA + "/" + pacienteId;
    }
    
}
