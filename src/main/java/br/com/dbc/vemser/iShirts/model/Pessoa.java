package br.com.dbc.vemser.iShirts.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "PESSOA")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PESSOA")
    @SequenceGenerator(name = "SEQ_PESSOA", sequenceName = "SEQ_PESSOA", allocationSize = 1)
    @Column(name = "ID_PESSOA")
    private Integer idPessoa;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "SOBRENOME")
    private String sobrenome;

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "CELULAR", nullable = false)
    private String celular;

    @Column(name = "DATA_NASCIMENTO")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    @Column(name = "PREFERENCIA", nullable = false)
    private String preferencia;

    @OneToMany(mappedBy = "pessoa")
    private List<Endereco> endereco;

    @Column(name = "ATIVO", nullable = false)
    private String ativo;

    @Column(name = "CRIADO", columnDefinition="TIMESTAMP")
    @CreationTimestamp
    private Date criado;

    @UpdateTimestamp
    @Column(name = "EDITADO", columnDefinition="TIMESTAMP")
    private Date editado;

    public void setIdUsuario(Integer idUsuario) {
        this.usuario = new Usuario();
        this.usuario.setIdUsuario(idUsuario);
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Set<Pedido> pedidos;

}