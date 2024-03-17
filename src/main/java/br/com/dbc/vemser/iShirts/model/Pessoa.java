package br.com.dbc.vemser.iShirts.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Date;

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
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
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

    @Column(name = "ATIVO", nullable = false)
    private char ativo;

    @Column(name = "CRIADO")
    private Date criado;

    @Column(name = "EDITADO")
    private Date editado;

}