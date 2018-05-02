package br.ufmt.ic.fata.PranchaComunicacao.controller.api;

import br.ufmt.ic.fata.PranchaComunicacao.model.TempoVerbal;
import br.ufmt.ic.fata.PranchaComunicacao.service.sintetizador.SintetizadorService;
import br.ufmt.ic.fata.PranchaComunicacao.util.converter.TempoVerbalConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/sintetizador")
public class SintetizadorController {
    
    private final SintetizadorService service;
    
    @Autowired
    public SintetizadorController(SintetizadorService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity sintetizarTexto(@RequestParam(value = "palavrasIds") List<String> palavrasIds,
                                          @RequestParam(value = "tempoVerbal") TempoVerbal tempoVerbal) {
        log.debug(service.getTextoFromListaPalavrasIds(palavrasIds, tempoVerbal));
        
        return null;
    }
    
    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(TempoVerbal.class, new TempoVerbalConverter());
    }
    
}
