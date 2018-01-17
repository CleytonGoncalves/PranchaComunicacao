package br.ufmt.ic.fata.PranchaComunicacao.modelo.base;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

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
    private Date dataCriacao;
    
    @LastModifiedDate
    private Date dataAtualizacao;
    
    //@CreatedBy
    //private Usuario usuarioCriador;
    
    //@LastModifiedBy
    //private String atualizadoPor;
    
    
    /* Date Getter/Setters Defensive Copies */
    
    public Date getDataCriacao() {
        return new Date(dataCriacao.getTime());
    }
    
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = new Date(dataCriacao.getTime());
    }
    
    public Date getDataAtualizacao() {
        return new Date(dataAtualizacao.getTime());
    }
    
    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = new Date(dataAtualizacao.getTime());
    }
    
}
