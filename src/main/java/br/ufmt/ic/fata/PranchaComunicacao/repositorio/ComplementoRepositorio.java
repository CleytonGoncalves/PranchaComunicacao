package br.ufmt.ic.fata.PranchaComunicacao.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Complemento;


@Repository
public interface ComplementoRepositorio extends JpaRepository<Complemento, Long> {
    
}
