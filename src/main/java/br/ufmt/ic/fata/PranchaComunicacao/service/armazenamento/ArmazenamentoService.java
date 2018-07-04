package br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento;

import org.springframework.core.io.Resource;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface ArmazenamentoService {
    
    /**
     * @param arquivo     - arquivo MultipartFile do Spring
     * @param nomeArquivo - nome para ser usado no arquivo.
     */
    void salvarUpload(MultipartFile arquivo, String nomeArquivo);
    
    /**
     * @param arquivo - arquivo MultipartFile do Spring
     */
    void salvarUpload(MultipartFile arquivo);
    
    void salvarInputStream(InputStream inputStream, String nomeArquivo);
    
    String salvarImagem(MultipartFile imagem, Errors erros);
    
    Stream<Path> carregarTodos();
    
    Path getCaminhoCompleto(String nomeArquivo);
    
    Resource carregarComoResource(String nomeArquivo);
    
    void removerTodos();
    
    boolean existeArquivo(String nomeArquivo);
    
    String gerarNomeUnico(String nomeOriginal);
    
}
