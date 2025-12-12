package br.com.gym.management.planapi.service;

import br.com.gym.management.planapi.domain.Inscricao;
import br.com.gym.management.planapi.domain.Plano;
import br.com.gym.management.planapi.repository.InscricaoRepository;
import br.com.gym.management.planapi.repository.PlanoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {

    private static final Logger log = LoggerFactory.getLogger(PlanoService.class);

    private final PlanoRepository planoRepository;
    private final InscricaoRepository inscricaoRepository;

    public PlanoService(PlanoRepository planoRepository, InscricaoRepository inscricaoRepository) {
        this.planoRepository = planoRepository;
        this.inscricaoRepository = inscricaoRepository;
    }

    @Transactional(readOnly = true)
    public List<Plano> findAll() {
        return planoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Plano> buscarPlanoPorIdComCache(Long planoId) {
        try {
            return buscarPlanoComCache(planoId);
        } catch (Exception e) {
            log.error(">>>> [CACHE FALLBACK] Falha ao acessar Redis. Recorrendo ao DB SQL. Erro: {}", e.getMessage());
            return planoRepository.findById(planoId);
        }
    }

    @Cacheable(value = "planos", key = "#planoId")
    public Optional<Plano> buscarPlanoComCache(Long planoId) {
        log.info(">>>> [CACHE MISS] Buscando Plano ID {} do banco de dados SQL (H2)...", planoId);
        return planoRepository.findById(planoId);
    }


    @Transactional(readOnly = true)
    public Optional<Inscricao> buscarInscricaoAtivaPorMembroId(Long membroId) {
        return inscricaoRepository.findByMembroIdAndAtivaTrue(membroId);
    }

    @Transactional
    public void desativarInscricoesAtivas(Long membroId) {
        List<Inscricao> inscricoesAtivas = inscricaoRepository.findAllByMembroIdAndAtivaTrue(membroId);

        if (!inscricoesAtivas.isEmpty()) {
            inscricoesAtivas.forEach(inscricao -> {
                inscricao.setAtiva(false);
            });
            inscricaoRepository.saveAll(inscricoesAtivas);
            log.info("Desativadas {} inscrições antigas para o Membro ID {}.", inscricoesAtivas.size(), membroId);
        }
    }
}