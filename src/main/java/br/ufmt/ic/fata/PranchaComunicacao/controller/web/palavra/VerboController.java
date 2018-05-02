package br.ufmt.ic.fata.PranchaComunicacao.controller.web.palavra;

import br.ufmt.ic.fata.PranchaComunicacao.model.Verbo;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.VerboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static br.ufmt.ic.fata.PranchaComunicacao.controller.web.palavra.PalavraController.PALAVRA_MODEL;

@Controller
@SessionAttributes(PALAVRA_MODEL) // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("/pastaVerbo") // URL raiz para todos os Requests deste controller
public class VerboController extends PalavraController<Verbo> {
    
    /* Nome das páginas HTML (Views) */
    private static final String PAGINA_INICIAL = "pastaVerbo";
    private static final String FRAGMENTO_CADASTRO = "pastaVerbo :: form-cadastro";
    
    @Autowired
    public VerboController(VerboService service) {
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
    Verbo novaInstanciaPalavra() {
        return new Verbo();
    }
    
}
