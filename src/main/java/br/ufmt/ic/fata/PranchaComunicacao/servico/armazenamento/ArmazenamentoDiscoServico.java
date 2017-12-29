package br.ufmt.ic.fata.PranchaComunicacao.servico.armazenamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import br.ufmt.ic.fata.PranchaComunicacao.util.NomeArquivoUtil;

@Service("ArmazenamentoServico")
public class ArmazenamentoDiscoServico implements ArmazenamentoServico {
    /* TODO: Criar entidade Imagem, que grave o nome recebido, tamanho, quem e quando */
    
    private final Path local;
    
    @Autowired
    public ArmazenamentoDiscoServico(@Value("${armazenamento.caminho}") Path local) {
        this.local = local;
    }
    
    @Override
    public void salvar(MultipartFile arquivo) {
        this.salvar(arquivo, arquivo.getOriginalFilename());
    }
    
    @Override
    public void salvar(MultipartFile arquivo, @NotNull String nomeArquivo) {
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
            
            Files.copy(arquivo.getInputStream(), this.local.resolve(nomeArquivo),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ArmazenamentoException("Falha ao armazenar o arquivo: " + nomeArquivo, e);
        }
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
    public Path carregar(String nomeArquivo) {
        return this.local.resolve(nomeArquivo);
    }
    
    @Override
    public Resource carregarComoResource(String nomeArquivo) {
        try {
            Path file = carregar(nomeArquivo);
            Resource resource = new UrlResource(file.toUri());
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
    
    @Override
    public String gerarNomeUnico(String nomeOriginal) {
        return NomeArquivoUtil.gerarNomeUnico(nomeOriginal, local);
    }
    
    /**
     * Cria o diretório de armazenamento de arquivos.
     * Deve ser chamado apenas durante a inicialização do Spring.
     */
    public void init() {
        try {
            Files.createDirectories(local);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível inicializar o local de " +
                                                    "armazenamento", e);
        }
    }
}
