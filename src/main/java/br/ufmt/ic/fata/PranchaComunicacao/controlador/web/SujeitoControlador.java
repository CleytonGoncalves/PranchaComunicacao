package br.ufmt.ic.fata.PranchaComunicacao.controlador.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.ufmt.ic.fata.PranchaComunicacao.excecao.FormUploadException;
import br.ufmt.ic.fata.PranchaComunicacao.excecao.PalavraNaoEncontradaException;
import br.ufmt.ic.fata.PranchaComunicacao.modelo.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.servico.PalavraServico;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("palavra1") // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("/pastaSujeito") // URL raiz para todos os Requests deste controller
public final class SujeitoControlador {
    
    private static final String PALAVRA_MODEL_NAME = "palavra1";
    
    private final PalavraServico<Sujeito> palavraServico;
    
    @Autowired
    public SujeitoControlador(PalavraServico<Sujeito> pS) {
        this.palavraServico = pS;
    }
    
    @GetMapping
    public String pegarRaiz() {
        return "pastaPalavra"; // View inicial
    }
    
    @ModelAttribute("listaPalavra")
    public List<Sujeito> popularSujeitosCadastrados() {
        return palavraServico.buscarTodas();
    }
    
    /**
     * Cria um novo sujeito no Model, retornando o fragmento referente a seu cadastro.
     *
     * @return Fragmento do Sujeito criado
     */
    @GetMapping("/novo")
    public String novoSujeito(Model model, SessionStatus status) {
        if (model.containsAttribute(PALAVRA_MODEL_NAME)) {
            status.setComplete();
        }
        
        model.addAttribute(PALAVRA_MODEL_NAME, new Sujeito());
        
        return "fragmentos/pastaSujeito :: modal-cadastro";
    }
    
    /**
     * Adiciona o sujeito ao Model, retornando o fragmento referente a seu cadastro.
     *
     * @return Fragmento do Sujeito Pedido
     */
    @GetMapping("/{id}")
    public String pegarSujeito(@PathVariable(name = "id") long id, Model model) {
        Optional<Sujeito> optSujeito = palavraServico.buscarPorId(id);
        
        if (! optSujeito.isPresent()) {
            // Só deve ocorrer a validação front-end não seja realizada, ou erro grave inesperado
            throw new PalavraNaoEncontradaException("ID pedido: " + id);
        }
    
        model.addAttribute(PALAVRA_MODEL_NAME, optSujeito.get());
    
        return "fragmentos/pastaSujeito :: modal-cadastro"; // Modal preenchido com o sujeito pedido
    }
    
    /**
     * Salva o Sujeito no ban
     *
     * @return Fragmento da tabela atualizado com o sujeito salvo
     */
    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.OK)
    public void salvarSujeito(@RequestParam("imagem") MultipartFile imagem,
                              @Valid @ModelAttribute(PALAVRA_MODEL_NAME) Sujeito sujeito,
                              BindingResult br, SessionStatus status) {
        
        if (sujeito.isNew() || ! imagem.isEmpty()) { // Novo sujeito, ou já existente com nova
            // imagem
            String imagemNome = palavraServico.salvarImagem(imagem, br);
            sujeito.setUrl(imagemNome);
        }
        
        if (br.hasErrors()) {
            // O Front-end é responsável por validar tudo antes de enviar. Só deve ocorrer caso
            // a validação front-end não seja realizada, ou erro grave inesperado
            FieldError erro = br.getFieldError();
            throw new FormUploadException("Sujeito " + erro.getField() + ": " + erro.getDefaultMessage());
        }
        
        palavraServico.salvar(sujeito);
        status.setComplete(); // Indica que terminou com o Sujeito atual
    }
    
}
