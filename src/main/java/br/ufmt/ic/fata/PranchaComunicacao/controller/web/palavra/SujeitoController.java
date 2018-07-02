package br.ufmt.ic.fata.PranchaComunicacao.controller.web.palavra;

import br.ufmt.ic.fata.PranchaComunicacao.model.Pronome;
import br.ufmt.ic.fata.PranchaComunicacao.model.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.SujeitoService;
import br.ufmt.ic.fata.PranchaComunicacao.util.converter.PronomeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static br.ufmt.ic.fata.PranchaComunicacao.controller.web.palavra.PalavraController.PALAVRA_MODEL;

@Controller
@SessionAttributes(PALAVRA_MODEL) // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("/pastaSujeito") // URL raiz para todos os Requests deste controller
public class SujeitoController extends PalavraController<Sujeito> {
    
    /* Nome das páginas HTML (Views) */
    private static final String PAGINA_INICIAL = "pastaSujeito";
    private static final String FRAGMENTO_CADASTRO = "pastaSujeito :: form-cadastro";
    
    @Autowired
    public SujeitoController(SujeitoService service) {
        super(service);
    }
    
    @Override
    String getPaginaInicial() {
        return PAGINA_INICIAL;
    }
    
    @Override
    String getFragmentoCadastro() {
        return FRAGMENTO_CADASTRO;
    }
    
    @Override
    Sujeito novaInstanciaPalavra() {
        return new Sujeito();
    }
    
    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(Pronome.class, new PronomeConverter());
    }
    
}
