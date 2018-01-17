package br.ufmt.ic.fata.PranchaComunicacao.servico.base;

import java.util.List;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.base.EntidadeBase;
import br.ufmt.ic.fata.PranchaComunicacao.repositorio.base.RepositorioBase;

public abstract class ServicoBase<T extends EntidadeBase> {
    
    protected abstract RepositorioBase<T> getRepositorio();
    
    public void salvar(T entidade) {
        getRepositorio().save(entidade);
    }
    
    public void remover(T entidade) {
        getRepositorio().delete(entidade);
    }
    
    public T buscarPorId(Long id) {
        return getRepositorio().findOne(id);
    }
    
    public List<T> buscarTodos() {
        return getRepositorio().findAll();
    }
    
}
