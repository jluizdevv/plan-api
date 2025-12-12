package br.com.gym.management.planapi.repository;

import br.com.gym.management.planapi.domain.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    @Query("SELECT i FROM Inscricao i JOIN FETCH i.plano WHERE i.membroId = :membroId AND i.ativa = true")
    Optional<Inscricao> findByMembroIdAndAtivaTrue(@Param("membroId") Long membroId);
    List<Inscricao> findAllByMembroIdAndAtivaTrue(Long membroId);
}