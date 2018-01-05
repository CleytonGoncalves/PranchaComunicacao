package br.ufmt.ic.fata.PranchaComunicacao.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PalavraRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
@Service("palavraServico")
public class PalavraServicoImpl<P extends Palavra> implements PalavraServico<P> {
    
    private final PalavraRepositorio palavraRepositorio;
    private final ArmazenamentoServico armazenamentoServico;
    
    private final ValidadorImagem validadorImagem;
    
    @Autowired
    public PalavraServicoImpl(PalavraRepositorio pR, ArmazenamentoServico aS,
                              ValidadorImagem vI) {
        this.palavraRepositorio = pR;
        this.armazenamentoServico = aS;
        this.validadorImagem = vI;
    }
    
    @Override
    public void salvar(Palavra palavra) {
        logPalavraSalva(palavra);
        palavraRepositorio.save(palavra);
    }
    
    @Override
    public String salvarImagem(MultipartFile imagem, Errors erros) {
        String novaImagemNome = armazenamentoServico.gerarNomeUnico(imagem.getOriginalFilename());
        
        validadorImagem.validate(imagem, erros);
        
        if (erros.hasErrors()) {
            return null;
        }
        
        armazenamentoServico.salvar(imagem, novaImagemNome);
        return novaImagemNome;
    }
    
    @Override
    public void remover(Palavra palavra) {
        palavraRepositorio.delete(palavra);
    }
    
    @Override
    public List<P> buscarPorPalavra(String palavra) {
        return (List<P>) palavraRepositorio.findByPalavra(palavra);
    }
    
    @Override
    public Optional<P> buscarPorId(Long id) {
        return Optional.of((P) palavraRepositorio.findOne(id));
    }
    
    @Override
    public List<P> buscarTodas() {
        return (List<P>) palavraRepositorio.findAll();
    }
    
    /*  Métodos Auxiliares */
    
    private void logPalavraSalva(Palavra palavra) {
        log.debug("\n--- Palavra Salva ---\n" +
                          "Palavra: " + palavra.getPalavra() + "\n" +
                          "Novo: " + palavra.isNew() + "\n" +
                          "ID: " + palavra.getId() + "\n" +
                          "Imagem: " + palavra.getUrl() + "\n" +
                          "--- ---\n");
    }
    
}
