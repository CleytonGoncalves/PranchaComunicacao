package br.ufmt.ic.fata.PranchaComunicacao.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufmt.ic.fata.PranchaComunicacao.modelo.Paciente;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
    
}
