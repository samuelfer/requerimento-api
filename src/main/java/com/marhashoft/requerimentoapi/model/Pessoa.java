package com.marhashoft.requerimentoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    private String email;

    private boolean ativo;

    @JsonIgnore
    @OneToMany(mappedBy = "vereador", fetch = FetchType.LAZY)
    private List<Requerimento> requerimentos;

    private boolean usuarioSistema;

    @ManyToOne
    @JoinColumn(name = "tipo_pessoa_id", foreignKey = @ForeignKey(name = "pessoa_tipo_pessoa"))
    private TipoPessoa tipoPessoa;

    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<Gestao> gestao = new ArrayList<>();

}
