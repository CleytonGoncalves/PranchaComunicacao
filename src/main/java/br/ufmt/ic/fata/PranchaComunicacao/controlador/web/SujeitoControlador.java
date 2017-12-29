package br.ufmt.ic.fata.PranchaComunicacao.controlador.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.servico.PalavraServico;
import br.ufmt.ic.fata.PranchaComunicacao.servico.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("palavra1") // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("/pastaSujeito") // URL raiz para todos os Requests deste controller
public final class SujeitoControlador {
    
    private static final String PALAVRA_MODEL_NAME = "palavra1";
    
    private final PalavraServico<Sujeito> palavraServico;
    private final ArmazenamentoServico armazenamentoServico;
    
    private final ValidadorImagem validadorImagem;
    
    @Autowired
    public SujeitoControlador(PalavraServico<Sujeito> pS, ArmazenamentoServico aS,
                              ValidadorImagem vI) {
        this.palavraServico = pS;
        this.armazenamentoServico = aS;
        this.validadorImagem = vI;
    }
    
    @GetMapping
    public String pegarRaiz(Model model) {
        model.addAttribute("armazenamentoServico", armazenamentoServico); // Para acessar as imagens
        
        return "pastaPalavra"; //View da raiz
    }
    
    @ModelAttribute("todosSujeitosCadastrados")
    public List<Sujeito> popularSujeitosCadastrados() {
        return palavraServico.buscarTodas();
    }
    
    @GetMapping("/novo")
    public String novoSujeito(Model model, SessionStatus status) {
        if (model.containsAttribute(PALAVRA_MODEL_NAME)) {
            status.setComplete();
        }
    
        model.addAttribute(PALAVRA_MODEL_NAME, new Sujeito());
        
        return "fragmentos/pastaSujeito :: modal-cadastro";
    }
    
    @PostMapping(value = "/salvar")
    public String salvarSujeito(@RequestParam("imagem") MultipartFile imagem,
                                @Valid @ModelAttribute(PALAVRA_MODEL_NAME) Sujeito sujeito,
                                BindingResult bR, Model model, RedirectAttributes ra,
                                SessionStatus status) {
        
        if (sujeito.isNew() || ! imagem.isEmpty()) { // Novo sujeito ou já existente sem nova imagem
            String novaImagemNome = armazenamentoServico.gerarNomeUnico(imagem.getOriginalFilename());
            
            validadorImagem.validate(imagem, bR);
            if (bR.hasErrors()) {
                //TODO: Adicionar validação de bR.hasErrors()
            }
            
            armazenamentoServico.salvar(imagem, novaImagemNome);
            sujeito.setUrl(novaImagemNome);
        }
        
        palavraServico.salvar(sujeito);
        status.setComplete(); // Indica que terminou com o Model
        
        //redirectAttributes.addFlashAttribute("message", "Sujeito " + sujeito.getPalavra() + "
        // salvo com sucesso!");
        
        return "redirect:/pastaSujeito";
    }
    
    @GetMapping("/{id}")
    public String pegarSujeito(@PathVariable(name = "id") long id, Model model) {
        Optional<Sujeito> optSujeito = palavraServico.buscarPorId(id);
        
        if (! optSujeito.isPresent()) {
            //TODO: Erro ID não existe
            return "";
        }
    
        model.addAttribute(PALAVRA_MODEL_NAME, optSujeito.get());
        
        return "fragmentos/pastaSujeito :: modal-cadastro";
    }
    
}
