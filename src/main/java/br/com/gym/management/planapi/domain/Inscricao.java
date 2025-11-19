package br.com.gym.management.planapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@Entity
@Table(name = "inscricoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "membro_id", nullable = false)
    private Long membroId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private Boolean ativa;
}