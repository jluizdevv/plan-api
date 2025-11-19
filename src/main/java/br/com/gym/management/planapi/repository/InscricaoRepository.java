package br.com.gym.management.planapi.repository;

import br.com.gym.management.planapi.domain.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    Optional<Inscricao> findByMembroIdAndAtivaTrue(Long membroId);
}