package br.ufmt.ic.fata.PranchaComunicacao.repositorio;

import org.springframework.data.repository.NoRepositoryBean;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Palavra;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.base.RepositorioBase;

@NoRepositoryBean
public interface PalavraRepositorio<T extends Palavra> extends RepositorioBase<T> {

}
