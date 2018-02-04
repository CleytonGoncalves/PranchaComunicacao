package br.ufmt.ic.fata.PranchaComunicacao.controlador.web.palavra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Diverso;
import br.ufmt.ic.fata.PranchaComunicacao.servico.palavra.DiversoServico;

@Controller
@SessionAttributes("palavra1") // Garante o mesmo Model até completar a sessão (setComplete())
@RequestMapping("/pastaDiverso") // URL raiz para todos os Requests deste controller
public class DiversoControlador extends PalavraControladorAbstrato<Diverso> {
    
    /* Nome das páginas HTML (Views) */
    private static final String PAGINA_INICIAL = "pastaDiverso";
    private static final String FRAGMENTO_CADASTRO = "pastaDiverso :: form-cadastro";
    
    @Autowired
    public DiversoControlador(DiversoServico servico) {
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
    Diverso novaInstanciaPalavra() {
        return new Diverso();
    }
    
}
