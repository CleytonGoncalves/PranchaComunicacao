package br.ufmt.ic.fata.PranchaComunicacao.service.armazenamento;

import br.ufmt.ic.fata.PranchaComunicacao.service.ValidadorImagem;
import br.ufmt.ic.fata.PranchaComunicacao.util.NomeArquivoUtil;
import br.ufmt.ic.fata.PranchaComunicacao.util.exception.ArmazenamentoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service("ArmazenamentoService")
public class ArmazenamentoDiscoService implements ArmazenamentoService {
    /* TODO: Criar entidade Imagem, que grave o nome recebido, tamanho, quem e quando */
    
    private final Path local;
    private final ValidadorImagem validadorImagem;
    
    @Autowired
    public ArmazenamentoDiscoService(@Value("${armazenamento.caminho}") Path local, ValidadorImagem vi) {
        this.local = local;
        this.validadorImagem = vi;
    }
    
    @Override
    public void salvarUpload(MultipartFile arquivo) {
        this.salvarUpload(arquivo, NomeArquivoUtil.transformarEmNomeValido(arquivo.getOriginalFilename
                                                                                     ()));
    }
    
    @Override
    public void salvarUpload(MultipartFile arquivo, String nomeArquivo) {
        try {
            if (nomeArquivo == null || nomeArquivo.isEmpty()) {
                throw new ArmazenamentoException("Nome do arquivo inválido: " + nomeArquivo);
            }
            if (arquivo.isEmpty()) {
                throw new ArmazenamentoException("Falha ao armazenar. Arquivo vazio: " +
                                                         nomeArquivo);
            }
            if (nomeArquivo.contains("..")) {
                // Checagem de segurança
                throw new ArmazenamentoException(
                        "Não é possível armazenar arquivo com caminho fora da pasta atual: "
                                + nomeArquivo);
            }
    
            salvarInputStream(arquivo.getInputStream(), nomeArquivo);
        } catch (IOException e) {
            throw new ArmazenamentoException("Falha ao armazenar o arquivo: " + nomeArquivo, e);
        }
    }
    
    public void salvarInputStream(InputStream inputStream, String nomeArquivo) {
        try {
            Files.copy(inputStream, this.local.resolve(nomeArquivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ArmazenamentoException("Falha ao armazenar o arquivo: " + nomeArquivo, e);
        }
    }
    
    public String salvarImagem(MultipartFile imagem, Errors erros) {
        String novaImagemNome = gerarNomeUnico(imagem.getOriginalFilename());
        validadorImagem.validate(imagem, erros);
        
        if (erros.hasErrors()) {
            return null;
        }
        
        salvarUpload(imagem, novaImagemNome);
        return novaImagemNome;
    }
    
    @Override
    public Stream<Path> carregarTodos() {
        try {
            return Files.walk(this.local, 1)
                        .filter(path -> ! path.equals(this.local))
                        .map(this.local::relativize);
        } catch (IOException e) {
            throw new ArmazenamentoException("Falha ao carregar os arquivos armazenados", e);
        }
    }
    
    @Override
    public Path getCaminhoCompleto(String nomeArquivo) {
        return this.local.resolve(nomeArquivo);
    }
    
    @Override
    public Resource carregarComoResource(String nomeArquivo) {
        try {
            Path caminho = getCaminhoCompleto(nomeArquivo);
            Resource resource = new UrlResource(caminho.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ArmazenamentoException("Não foi possível ler o arquivo. Não encontrado:" +
                                                         " " + nomeArquivo);
            }
        } catch (MalformedURLException e) {
            throw new ArmazenamentoException("Não foi possível ler o arquivo. Não encontrado: " +
                                                     nomeArquivo, e);
        }
    }
    
    @Override
    public void removerTodos() {
        FileSystemUtils.deleteRecursively(local.toFile());
    }
    
    public boolean existeArquivo(String nomeArquivo) {
        Path caminho = getCaminhoCompleto(nomeArquivo);
        return Files.exists(caminho);
    }
    
    @Override
    public String gerarNomeUnico(String nomeOriginal) {
        return NomeArquivoUtil.gerarNomeUnico(nomeOriginal, local);
    }
    
}
