package br.ufmt.ic.fata.PranchaComunicacao.controlador.web.palavra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.servico.palavra.SujeitoServico;

import static br.ufmt.ic.fata.PranchaComunicacao.controlador.web.palavra
                      .PalavraControladorAbstrato.PALAVRA_MODEL;

@Controller
@SessionAttributes(PALAVRA_MODEL) // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("/pastaSujeito") // URL raiz para todos os Requests deste controller
public class SujeitoControlador extends PalavraControladorAbstrato<Sujeito> {
    
    /* Nome das páginas HTML (Views) */
    private static final String PAGINA_INICIAL = "pastaSujeito";
    private static final String FRAGMENTO_CADASTRO = "pastaSujeito :: form-cadastro";
    
    @Autowired
    public SujeitoControlador(SujeitoServico servico) {
        super(servico);
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
    
}
