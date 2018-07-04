package br.ufmt.ic.fata.PranchaComunicacao.service.paciente;

import br.ufmt.ic.fata.PranchaComunicacao.model.Complemento;
import br.ufmt.ic.fata.PranchaComunicacao.model.Diverso;
import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.model.Sujeito;
import br.ufmt.ic.fata.PranchaComunicacao.model.Verbo;
import br.ufmt.ic.fata.PranchaComunicacao.repository.PacienteRepository;
import br.ufmt.ic.fata.PranchaComunicacao.repository.base.BaseRepository;
import br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento.ArmazenamentoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.base.BaseCrudService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.ComplementoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.DiversoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.SujeitoService;
import br.ufmt.ic.fata.PranchaComunicacao.service.palavra.VerboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public class PacienteService extends BaseCrudService<Paciente> {
    
    private final ArmazenamentoService armazenamentoService;
    
    private final SujeitoService sujeitoService;
    private final VerboService verboService;
    private final ComplementoService complementoService;
    private final DiversoService diversoService;
    
    private final PacienteRepository repository;
    
    public PacienteService(PacienteRepository repo, ArmazenamentoService as, SujeitoService sujeitoService,
                           VerboService verboService, ComplementoService complementoService, DiversoService diversoService) {
        this.repository = repo;
        this.armazenamentoService = as;
        this.sujeitoService = sujeitoService;
        this.verboService = verboService;
        this.complementoService = complementoService;
        this.diversoService = diversoService;
    }
    
    public String salvarImagem(MultipartFile imagem, Errors erros) {
        return armazenamentoService.salvarImagem(imagem, erros);
    }
    
    public void adicionarPalavras(Paciente paciente, List<String> listaPalavra) {
        for (String palavraId : listaPalavra) {
            palavraId = palavraId.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "").trim();
            if (palavraId.isEmpty()) { continue; }
            String[] palavraArr = palavraId.split("-"); // Formato: "sujeito-3"
            String tipo = palavraArr[0];
            Long id = Long.parseLong(palavraArr[1]);
        
            switch (tipo) {
                case "sujeito":
                    Sujeito palavra1 = sujeitoService.buscarPorId(id);
                    Set<Sujeito> set1 = paciente.getSujeitos();
                    set1.add(palavra1);
                    paciente.setSujeitos(set1);
                    break;
                case "verbo":
                    Verbo palavra2 = verboService.buscarPorId(id);
                    Set<Verbo> set2 = paciente.getVerbos();
                    set2.add(palavra2);
                    paciente.setVerbos(set2);
                    break;
                case "complemento":
                    Complemento palavra3 = complementoService.buscarPorId(id);
                    Set<Complemento> set3 = paciente.getComplementos();
                    set3.add(palavra3);
                    paciente.setComplementos(set3);
                    break;
                case "diverso":
                    Diverso palavra4 = diversoService.buscarPorId(id);
                    Set<Diverso> set4 = paciente.getDiversos();
                    set4.add(palavra4);
                    paciente.setDiversos(set4);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de palavra desconhecido");
            }
        }
    
    }
    
    public List<Sujeito> getTodosSujeitos() {
        return sujeitoService.buscarTodos();
    }
    
    public List<Verbo> getTodosVerbos() {
        return verboService.buscarTodos();
    }
    
    public List<Complemento> getTodosComplementos() {
        return complementoService.buscarTodos();
    }
    
    public List<Diverso> getTodosDiversos() {
        return diversoService.buscarTodos();
    }
    
    @Override
    protected BaseRepository<Paciente> getRepository() {
        return repository;
    }
    
}
