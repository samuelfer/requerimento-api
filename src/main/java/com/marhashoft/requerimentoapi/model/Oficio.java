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
@Table(name = "oficio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Oficio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oficio_seq")
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    @NotEmpty
    @Size(min = 5, message = "Assunto precisa ter no mínimo 5 caracteres")
    private String texto;

    @Column(nullable = false, columnDefinition = "text")
    @NotEmpty
    @Size(min = 5, message = "Assunto precisa ter no mínimo 5 caracteres")
    private String assunto;

    private String numero;

    private LocalDateTime dataOficio = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "assinante_id", nullable=false)
    private Pessoa assinante;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usuario_id", nullable=false)
    private Usuario usuario;

    private String destinatario;

    private String cargoDestinatario;

    @ManyToOne
    @JoinColumn(name = "pronome_tratamento_id", nullable=false)
    private PronomeTratamento pronomeTratamento;
}
