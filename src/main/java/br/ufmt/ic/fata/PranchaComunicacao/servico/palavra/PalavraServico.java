package br.ufmt.ic.fata.PranchaComunicacao.servico.palavra;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.PalavraRepositorio;
import br.ufmt.ic.fata.PranchaComunicacao.servico.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento.ArmazenamentoServico;
import br.ufmt.ic.fata.PranchaComunicacao.servico.base.ServicoBase;
import lombok.extern.slf4j.Slf4j;

/**
 * <a href=https://stackoverflow.com/q/48125594/9180376>Por que não é possível fazer um único
 * PalavraServico.</a>
 *
 * @param <T> Subclasse de palavra
 */
@Slf4j
public abstract class PalavraServico<T extends Palavra> extends ServicoBase<T> {
    
    private final ArmazenamentoServico armazenamentoServico;
    
    private final ValidadorImagem validadorImagem;
    
    protected abstract PalavraRepositorio<T> getRepositorio();
    
    PalavraServico(ArmazenamentoServico as, ValidadorImagem vi) {
        this.armazenamentoServico = as;
        this.validadorImagem = vi;
    }
    
    @Override
    public void salvar(T palavra) {
        logPalavraSalva(palavra);
        super.salvar(palavra);
    }
    
    public String salvarImagem(MultipartFile imagem, Errors erros) {
        String novaImagemNome = armazenamentoServico.gerarNomeUnico(imagem.getOriginalFilename());
        
        validadorImagem.validate(imagem, erros);
        
        if (erros.hasErrors()) {
            return null;
        }
        
        armazenamentoServico.salvar(imagem, novaImagemNome);
        return novaImagemNome;
    }
    
    /*  Métodos Auxiliares */
    
    private void logPalavraSalva(T palavra) {
        log.debug("\n--- Palavra Salva ---\n" +
                          "Palavra: " + palavra.getPalavra() + "\n" +
                          "Novo: " + palavra.isNew() + "\n" +
                          "ID: " + palavra.getId() + "\n" +
                          "Imagem: " + palavra.getUrl() + "\n" +
                          "--- ---\n");
    }
    
}
