package br.ufmt.ic.fata.PranchaComunicacao.util;

import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.Normalizer;

public final class NomeArquivoUtil {
    
    public static String gerarNomeUnico(String nomeOriginal, Path local) {
        if (nomeOriginal == null || nomeOriginal.isEmpty()) {
            nomeOriginal = org.thymeleaf.util.StringUtils.randomAlphanumeric(10);
        }
        
        String novoNome = transformarEmNomeValido(nomeOriginal);
        
        String nomeSimples = StringUtils.stripFilenameExtension(novoNome);
        String extensao = StringUtils.getFilenameExtension(novoNome);
        
        // Para não sobrescrever arquivos de nome igual
        int sufixo = 1;
        while (Files.exists(local.resolve(novoNome), LinkOption.NOFOLLOW_LINKS)) {
            novoNome = (nomeSimples + sufixo) + extensao;
            sufixo++;
        }
        
        return novoNome;
    }
    
    public static String transformarEmNomeValido(String nomeOriginal) {
        return removerAcentos(StringUtils.cleanPath(nomeOriginal)).toLowerCase();
    }
    
    private static String removerAcentos(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        
        return s;
    }
    
}
