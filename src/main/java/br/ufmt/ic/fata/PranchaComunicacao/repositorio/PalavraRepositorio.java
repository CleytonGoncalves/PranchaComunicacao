package br.ufmt.ic.fata.PranchaComunicacao.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;

@NoRepositoryBean
public interface PalavraRepositorio<T extends Palavra> extends JpaRepository<T, Long> {

}
