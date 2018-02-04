package br.ufmt.ic.fata.PranchaComunicacao.modelo.base;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Para ser usado quando implementar o Spring Security */
/* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing */
@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public abstract class EntidadeBaseAuditavel extends EntidadeBase {
    //implements Auditable<User, Long> {
    
    @CreatedDate
    private ZonedDateTime dataCriacao;
    
    @LastModifiedDate
    private ZonedDateTime dataAtualizacao;
    
    //@CreatedBy
    //private Usuario usuarioCriador;
    
    //@LastModifiedBy
    //private String atualizadoPor;
    
}
