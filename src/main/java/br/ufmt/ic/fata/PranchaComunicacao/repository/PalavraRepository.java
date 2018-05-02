package br.ufmt.ic.fata.PranchaComunicacao.repository;

import br.ufmt.ic.fata.PranchaComunicacao.model.Palavra;
import br.ufmt.ic.fata.PranchaComunicacao.repository.base.BaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PalavraRepository<T extends Palavra> extends BaseRepository<T> {

}
