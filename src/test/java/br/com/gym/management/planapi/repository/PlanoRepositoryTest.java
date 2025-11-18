package br.com.gym.management.planapi.repository;

import br.com.gym.management.planapi.domain.Plano;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlanoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlanoRepository planoRepository;

    @Test
    @DisplayName("Deve retornar um plano quando buscar por um ID existente")
    void quandoBuscarPorId_deveRetornarPlano() {
        Plano plano = new Plano(null, "Plano Teste", new BigDecimal("199.90"), 3);
        Plano planoSalvo = entityManager.persistAndFlush(plano);

        Optional<Plano> planoEncontrado = planoRepository.findById(planoSalvo.getId());

        assertThat(planoEncontrado).isPresent();
        assertThat(planoEncontrado.get().getNome()).isEqualTo("Plano Teste");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para ID inexistente")
    void quandoBuscarPorIdInexistente_deveRetornarVazio() {
        Optional<Plano> planoEncontrado = planoRepository.findById(99L);

        assertThat(planoEncontrado).isNotPresent();
    }
}