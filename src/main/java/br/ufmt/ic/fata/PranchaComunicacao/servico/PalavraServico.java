package br.ufmt.ic.fata.PranchaComunicacao.servico;

import com.sun.istack.internal.Nullable;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;

public interface PalavraServico<P extends Palavra> {
    
    void salvar(Palavra palavra);
    
    @Nullable
    String salvarImagem(MultipartFile imagem, Errors erros);
    
    void remover(Palavra palavra);
    
    List<P> buscarPorPalavra(String palavra);
    
    Optional<P> buscarPorId(Long id);
    
    List<P> buscarTodas();
    
}
