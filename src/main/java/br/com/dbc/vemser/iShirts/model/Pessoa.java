package br.com.dbc.vemser.iShirts.model;

import javax.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "PESSOA")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PESSOA")
    @SequenceGenerator(name = "SEQ_PESSOA", sequenceName = "SEQ_PESSOA", allocationSize = 1)
    @Column(name = "ID_PESSOA")
    private Integer idPessoa;

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
    private Date dataNascimento;

    @Column(name = "PREFERENCIA", nullable = false)
    private String preferencia;

    @OneToMany(mappedBy = "pessoa")
    private List<Endereco> endereco;

    @Column(name = "ATIVO", nullable = false)
    private String ativo;

    @Column(name = "CRIADO", columnDefinition="TIMESTAMP")
    private Date criado;

    @Column(name = "EDITADO", columnDefinition="TIMESTAMP")
    private Date editado;


}