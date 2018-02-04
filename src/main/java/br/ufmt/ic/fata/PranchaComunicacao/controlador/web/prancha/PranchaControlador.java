package br.ufmt.ic.fata.PranchaComunicacao.controlador.web.prancha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.servico.prancha.PranchaServico;

@Controller
@SessionAttributes("paciente1") // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("pranchaComunicacao")
public class PranchaControlador {
    
    /* Nome das páginas HTML (Views) */
    private static final String PAGINA_INICIAL_SEM_PACIENTE = "pastaPaciente";
    private static final String PAGINA_INICIAL_COM_PACIENTE = "pranchaComunicacao";
    
    /* Identificador do Paciente no template HTML */
    private static final String PACIENTE_MODEL = "paciente1";
    
    private final PranchaServico servico;
    
    @Autowired
    public PranchaControlador(PranchaServico servico) {
        this.servico = servico;
    }
    
    /**
     * @return Página para seleção de paciente
     */
    @GetMapping
    public String getPaginaInicialSemPaciente() {
        return PAGINA_INICIAL_SEM_PACIENTE;
    }
    
    @GetMapping("/{pacienteId}")
    public String getPaginaInicialComPaciente(@PathVariable(name = "pacienteId") long id,
                                              Model model, SessionStatus status) {
        
        Paciente paciente = servico.getPacientePorId(id);
        if (paciente == null) { return getPaginaInicialSemPaciente(); }
        
        if (model.containsAttribute(PACIENTE_MODEL)) {
            status.setComplete();
        }
        
        model.addAttribute(PACIENTE_MODEL, paciente);
        
        return PAGINA_INICIAL_COM_PACIENTE;
    }
    
}
