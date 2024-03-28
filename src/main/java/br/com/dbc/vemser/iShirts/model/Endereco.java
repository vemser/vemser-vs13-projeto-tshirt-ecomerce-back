package br.com.dbc.vemser.iShirts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "Endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ENDERECO")
    @SequenceGenerator(name = "SEQ_ENDERECO",sequenceName = "SEQ_ENDERECO",allocationSize = 1)
    @Column(name = "ID_ENDERECO")
    private Integer idEndereco;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "id_pessoa",referencedColumnName = "id_pessoa")
    @JsonIgnore
    private Pessoa pessoa;

    @Column(name = "id_Pessoa",updatable = false,insertable = false)
    private Integer idPessoa;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "Cidade")
    private String cidade;

    @Column(name = "cep")
    private String cep;

    @Column(name = "estado")
    private String estado;

    @Column(name = "pais")
    private String pais;

    @Column(name = "criado",updatable = false)
    @CreationTimestamp
    private Timestamp criadoEm;

    @Column(name = "editado")
    @UpdateTimestamp
    private Timestamp editadoEm;



}
