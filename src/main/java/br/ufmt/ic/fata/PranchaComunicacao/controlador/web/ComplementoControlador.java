package br.ufmt.ic.fata.PranchaComunicacao.controlador.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Complemento;
import br.ufmt.ic.fata.PranchaComunicacao.servico.palavra.ComplementoServico;

@Controller
@SessionAttributes("palavra1") // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("/pastaComplemento") // URL raiz para todos os Requests deste controller
public class ComplementoControlador extends PalavraControladorAbstrato<Complemento> {
    
    /* Nome das páginas HTML (Views) */
    private static final String PAGINA_INICIAL = "pastaComplemento";
    private static final String FRAGMENTO_CADASTRO = "pastaComplemento :: form-cadastro";
    
    @Autowired
    public ComplementoControlador(ComplementoServico servico) {
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
    Complemento novaInstanciaPalavra() {
        return new Complemento();
    }
    
}
