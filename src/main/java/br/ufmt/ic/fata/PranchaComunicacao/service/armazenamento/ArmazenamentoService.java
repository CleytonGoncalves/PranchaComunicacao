package br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ArmazenamentoService {
    
    /**
     * @param arquivo     - arquivo MultipartFile do Spring
     * @param nomeArquivo - nome para ser usado no arquivo.
     */
    void salvar(MultipartFile arquivo, String nomeArquivo);
    
    /**
     * @param arquivo - arquivo MultipartFile do Spring
     */
    void salvar(MultipartFile arquivo);
    
    Stream<Path> carregarTodos();
    
    Path carregar(String nomeArquivo);
    
    Resource carregarComoResource(String nomeArquivo);
    
    void removerTodos();
    
    String gerarNomeUnico(String nomeOriginal);
    
}
