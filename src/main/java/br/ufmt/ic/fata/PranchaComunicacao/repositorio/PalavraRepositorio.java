package br.ufmt.ic.fata.PranchaComunicacao.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;

public interface PalavraRepositorio extends JpaRepository<Palavra, Long> {
    
    List<Palavra> findByPalavra(String palavra);
}
