package br.ufmt.ic.fata.PranchaComunicacao.controller.web.palavra;

import br.ufmt.ic.fata.PranchaComunicacao.model.Palavra;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.PalavraService;
import br.ufmt.ic.fata.PranchaComunicacao.util.exception.FormUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public abstract class PalavraController<T extends Palavra> {
    
    /* Identificador da palavra no template HTML */
    static final String PALAVRA_MODEL = "palavra1";
    
    /* Serviço da Palavra */
    private final PalavraService<T> palavraService;
    
    PalavraController(PalavraService<T> ps) {
        this.palavraService = ps;
    }
    
    @GetMapping
    public String pegarPaginaInicial() {
        return getPaginaInicial();
    }
    
    @ModelAttribute("listaPalavra")
    public List<T> listaPalavra() {
        return palavraService.buscarTodos();
    }
    
    /**
     * Cria uma nova palavra no Model, retornando o fragmento referente a seu cadastro.
     *
     * @return Fragmento da Palavra criada
     */
    @GetMapping("/novo")
    public String novaPalavra(Model model, SessionStatus status) {
        if (model.containsAttribute(PALAVRA_MODEL)) {
            status.setComplete();
        }
        
        model.addAttribute(PALAVRA_MODEL, novaInstanciaPalavra());
        
        return getFragmentoCadastro();
    }
    
    /**
     * Adiciona a Palavra ao Model, retornando o fragmento referente a seu cadastro.
     *
     * @return Fragmento da Palavra pedida
     */
    @GetMapping("/{id}")
    public String pegarPalavraPorId(@PathVariable(name = "id") long id, Model model) {
        model.addAttribute(PALAVRA_MODEL, palavraService.buscarPorId(id));
        
        return getFragmentoCadastro(); // Fragmento preenchido com a palavra pedida
    }
    
    /**
     * Salva o Palavra no banco e retorna OK (HTTP Code 200) se bem sucedido
     */
    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.OK)
    public void salvarPalavra(@RequestParam("imagem") MultipartFile imagem,
                              @Valid @ModelAttribute(PALAVRA_MODEL) T palavra,
                              BindingResult br, SessionStatus status) {
        
        if (palavra.isNew() || ! imagem.isEmpty()) { //Nova palavra, ou já existente com nova imagem
            String imagemNome = palavraService.salvarImagem(imagem, br);
            palavra.setUrl(imagemNome);
        }
        
        if (br.hasErrors()) {
            // O Front-end é responsável por validar tudo antes de enviar. Só deve ocorrer caso
            // a validação front-end não seja realizada, ou erro grave inesperado
            FieldError erro = br.getFieldError();
            throw new FormUploadException("Palavra " + erro.getField() + ": " + erro.getDefaultMessage());
        }
        
        palavraService.salvar(palavra);
        status.setComplete(); // Indica que terminou com a Palavra atual
    }
    
    /* Métodos Abstratos */
    
    abstract String getPaginaInicial();
    
    abstract String getFragmentoCadastro();
    
    abstract T novaInstanciaPalavra();
    
}
