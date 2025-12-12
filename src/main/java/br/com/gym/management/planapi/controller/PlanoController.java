package br.com.gym.management.planapi.controller;

import br.com.gym.management.planapi.domain.Inscricao;
import br.com.gym.management.planapi.domain.Plano;
import br.com.gym.management.planapi.service.PlanoService; // NOVO IMPORT
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping
    public List<Plano> listarPlanos() {
        return planoService.findAll();
    }

    @GetMapping("/{planoId}")
    public ResponseEntity<Plano> buscarPlanoPorId(@PathVariable Long planoId) {
        return planoService.buscarPlanoPorIdComCache(planoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/inscricoes/membro/{membroId}")
    @Transactional(readOnly = true)
    public ResponseEntity<Optional<Inscricao>> buscarInscricaoAtivaPorMembroId(@PathVariable Long membroId) {
        Optional<Inscricao> inscricao = planoService.buscarInscricaoAtivaPorMembroId(membroId);

        return ResponseEntity.ok(inscricao);
    }
}