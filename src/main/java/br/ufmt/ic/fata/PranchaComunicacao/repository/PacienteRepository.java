package br.ufmt.ic.fata.PranchaComunicacao.repository;

import br.ufmt.ic.fata.PranchaComunicacao.model.Paciente;
import br.ufmt.ic.fata.PranchaComunicacao.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends BaseRepository<Paciente> {
    
}
