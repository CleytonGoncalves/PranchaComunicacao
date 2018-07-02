package br.ufmt.ic.fata.PranchaComunicacao.service.base;

import br.ufmt.ic.fata.PranchaComunicacao.model.base.EntidadeBase;
import br.ufmt.ic.fata.PranchaComunicacao.repository.base.BaseRepository;

import java.util.List;

public abstract class BaseCrudService<T extends EntidadeBase> {
    
    protected abstract BaseRepository<T> getRepository();
    
    public void salvar(T entidade) {
        getRepository().save(entidade);
    }
    
    public void remover(T entidade) {
        getRepository().delete(entidade);
    }
    
    public T buscarPorId(Long id) {
        return getRepository().findOne(id);
    }
    
    public List<T> buscarTodos() {
        return getRepository().findAll();
    }
    
}
