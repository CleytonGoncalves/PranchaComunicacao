package br.ufmt.ic.fata.PranchaComunicacao.controller.web.paciente;

import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.service.paciente.PacienteService;
import br.ufmt.ic.fata.PranchaComunicacao.util.exception.FormUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("paciente") // Garante o mesmo Model até completar o cadastro
@RequestMapping("/pastaPaciente")
public class PacienteController {
    
    private static final String PAGINA_INICIAL = "pastaPaciente";
    private static final String PAGINA_PRANCHA = "pranchaComunicacao";
    private static final String PAGINA_CADASTRO = "cadastroPaciente";
    
    /* Identificador do Paciente no template HTML */
    private static final String PACIENTE_MODEL = "paciente";
    
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
    public String getPaginaInicial(Model model, SessionStatus status) {
        if (model.containsAttribute(PACIENTE_MODEL)) {
            status.setComplete();
        }
        
        return PAGINA_INICIAL;
    }
    
    @GetMapping("/selecionarPrancha/{id}")
    public String selecionarPrancha(@PathVariable(name = "id") long pacienteId) {
        return "redirect:/" + PAGINA_PRANCHA + "/" + pacienteId;
    }
    
    /* Cadastro / Atualização */
    
    @GetMapping("/novo")
    public String cadastrarPaciente(Model model, SessionStatus status) {
        if (model.containsAttribute(PACIENTE_MODEL)) {
            status.setComplete();
        }
        
        model.addAttribute(PACIENTE_MODEL, new Paciente());
        addPalavrasNoModel(model);
        
        return PAGINA_CADASTRO;
    }
    
    @GetMapping("/{id}")
    public String atualizarPaciente(@PathVariable(name = "id") long id, Model model, SessionStatus status) {
        if (model.containsAttribute(PACIENTE_MODEL)) {
            status.setComplete();
        }
        
        model.addAttribute(PACIENTE_MODEL, service.buscarPorId(id));
        addPalavrasNoModel(model);
        
        return PAGINA_CADASTRO;
    }
    
    @PostMapping(value = "/salvar")
    public String salvarPaciente(@RequestParam("imagem") MultipartFile imagem,
                                 @Valid @ModelAttribute(PACIENTE_MODEL) Paciente paciente,
                                 @RequestParam("palavrasIds") List<String> palavrasIds,
                                 BindingResult br, SessionStatus status) {
        
        if (paciente.isNew() || ! imagem.isEmpty()) { //Nova paciente, ou já existente com nova imagem
            String imagemNome = service.salvarImagem(imagem, br);
            paciente.setImagemUrl(imagemNome);
        }
        
        service.adicionarPalavras(paciente, palavrasIds);
        
        if (br.hasErrors()) {
            // O Front-end é responsável por validar tudo antes de enviar. Só deve ocorrer caso
            // a validação front-end não seja realizada, ou erro grave inesperado
            FieldError erro = br.getFieldError();
            throw new FormUploadException("Paciente " + erro.getField() + ": " + erro.getDefaultMessage());
        }
        
        service.salvar(paciente);
        status.setComplete(); // Indica que terminou com o Paciente atual
        
        return "redirect:/" + PAGINA_PRANCHA;
    }
    
    public void addPalavrasNoModel(Model model) {
        model.addAttribute(service.getTodosSujeitos());
        model.addAttribute(service.getTodosVerbos());
        model.addAttribute(service.getTodosComplementos());
        model.addAttribute(service.getTodosDiversos());
    }
    
}
