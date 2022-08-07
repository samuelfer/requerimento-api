package com.marhashoft.requerimentoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "requerimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Requerimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requerimento_seq")
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    @NotEmpty
    @Size(min = 5, message = "Assunto precisa ter no mínimo 5 caracteres")
    private String assunto;

    private String numero;

    private LocalDateTime dataRequerimento = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pessoa_id", nullable=false)
    private Pessoa pessoa;
}
