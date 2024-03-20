package br.com.dbc.vemser.iShirts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Foto")
@Table(name = "FOTO")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FOTO")
    @SequenceGenerator(name = "SEQ_FOTO", sequenceName = "SEQ_FOTO", allocationSize = 1)
    @Column(name = "ID_FOTO")
    private Integer idFoto;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "ARQUIVO")
    private byte[] arquivo;
}
