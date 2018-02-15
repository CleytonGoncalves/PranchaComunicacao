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
// Garante o mesmo Model até completar a sessão (setComplete())
@SessionAttributes({"pacienteId", "sujeitos", "verbos", "complementos", "diversos"})
@RequestMapping("pranchaComunicacao")
public class PranchaControlador {
    
    /* Nome das páginas HTML (Views) */
    private static final String PAGINA_INICIAL_SEM_PACIENTE = "pastaPaciente";
    private static final String PAGINA_INICIAL_COM_PACIENTE = "pranchaComunicacao";
    
    /* Identificador do Paciente no template HTML */
    private static final String PACIENTE_ID_MODEL = "pacienteId";
    private static final String PACIENTE_IMAGEM_MODEL = "pacienteImagem";
    private static final String SUJEITOS_MODEL = "listaSujeito";
    private static final String VERBOS_MODEL = "listaVerbo";
    private static final String COMPLEMENTOS_MODEL = "listaComplemento";
    private static final String DIVERSOS_MODEL = "listaDiverso";
    
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
        return "redirect:/" + PAGINA_INICIAL_SEM_PACIENTE;
    }
    
    @GetMapping("/{pacienteId}")
    public String getPaginaInicialComPaciente(@PathVariable(name = "pacienteId") long id,
                                              Model model, SessionStatus status) {
        
        Paciente paciente = servico.getPacientePorId(id);
        if (paciente == null) { return getPaginaInicialSemPaciente(); }
    
        if (model.containsAttribute(PACIENTE_ID_MODEL)) {
            status.setComplete();
        }
    
        addPacienteNoModel(model, paciente);
    
        return PAGINA_INICIAL_COM_PACIENTE;
    }
    
    /* Métodos ajudantes */
    
    private void addPacienteNoModel(Model model, Paciente paciente) {
        model.addAttribute(PACIENTE_ID_MODEL, paciente.getId());
        model.addAttribute(PACIENTE_IMAGEM_MODEL, paciente.getImagemUrl());
        model.addAttribute(SUJEITOS_MODEL, paciente.getSujeitos());
        model.addAttribute(VERBOS_MODEL, paciente.getVerbos());
        model.addAttribute(COMPLEMENTOS_MODEL, paciente.getComplementos());
        model.addAttribute(DIVERSOS_MODEL, paciente.getDiversos());
    }
    
}
