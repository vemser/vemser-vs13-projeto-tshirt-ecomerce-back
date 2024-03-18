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
@Entity(name = "FOTOS")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FOTOS")
    @SequenceGenerator(name = "SEQ_FOTOS", sequenceName = "SEQ_FOTOS", allocationSize = 1)
    @Column(name = "ID_FOTO")
    private Integer idFoto;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "ARQUIVO")
    private byte[] arquivo;
}
