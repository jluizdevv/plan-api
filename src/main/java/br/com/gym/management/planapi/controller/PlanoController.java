package br.com.gym.management.planapi.controller;

import br.com.gym.management.planapi.domain.Plano;
import br.com.gym.management.planapi.repository.PlanoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    private final PlanoRepository planoRepository;

    public PlanoController(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
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
}