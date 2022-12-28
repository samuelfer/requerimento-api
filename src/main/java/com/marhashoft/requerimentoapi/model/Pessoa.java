package com.marhashoft.requerimentoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
    private Long id;

    @Column(nullable = false)
    private String nome;
    private String cargo;
    private boolean ativo;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<Requerimento> requerimentos;

    @ManyToOne
    @JoinColumn(name = "tipo_pessoa_id", foreignKey = @ForeignKey(name = "pessoa_tipo_pessoa"))
    private TipoPessoa tipoPessoa;
}
