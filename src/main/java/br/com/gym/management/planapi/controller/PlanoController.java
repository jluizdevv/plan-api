package br.com.gym.management.planapi.controller;

import br.com.gym.management.planapi.domain.Inscricao;
import br.com.gym.management.planapi.domain.Plano;
import br.com.gym.management.planapi.repository.InscricaoRepository;
import br.com.gym.management.planapi.repository.PlanoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    private final PlanoRepository planoRepository;
    private final InscricaoRepository inscricaoRepository;

    public PlanoController(PlanoRepository planoRepository, InscricaoRepository inscricaoRepository) {
        this.planoRepository = planoRepository;
        this.inscricaoRepository = inscricaoRepository;
    }

    @GetMapping
    public List<Plano> listarPlanos() {
        return planoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPlanoPorId(@PathVariable Long id) {
        return planoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/inscricoes/membro/{membroId}")
    @Transactional(readOnly = true)
    public ResponseEntity<Inscricao> buscarInscricaoAtivaPorMembroId(@PathVariable Long membroId) {
        Optional<Inscricao> inscricao = inscricaoRepository.findByMembroIdAndAtivaTrue(membroId);

        return inscricao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}