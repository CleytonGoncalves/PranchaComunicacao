package br.ufmt.ic.fata.PranchaComunicacao.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Verbo;

@Repository
public interface VerboRepositorio extends JpaRepository<Verbo, Long> {
    
}
