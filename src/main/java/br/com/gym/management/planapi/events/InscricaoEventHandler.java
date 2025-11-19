package br.com.gym.management.planapi.events;

import br.com.gym.management.planapi.domain.Inscricao;
import br.com.gym.management.planapi.domain.Plano;
import br.com.gym.management.planapi.repository.InscricaoRepository;
import br.com.gym.management.planapi.repository.PlanoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class InscricaoEventHandler {

    private final PlanoRepository planoRepository;
    private final InscricaoRepository inscricaoRepository;

    public InscricaoEventHandler(PlanoRepository planoRepository, InscricaoRepository inscricaoRepository) {
        this.planoRepository = planoRepository;
        this.inscricaoRepository = inscricaoRepository;
    }

    @RabbitListener(queues = "inscricao.criar")
    @Transactional
    public void handleInscricaoCriada(InscricaoCriadaEvent event) {

        System.out.println("--- NOVO EVENTO RECEBIDO ---");

        Plano plano = planoRepository.findById(event.planoId()).orElse(null);

        if (plano == null) {
            System.err.println("ERRO: Plano não encontrado para ID: " + event.planoId() + ". Inscrição cancelada.");
            return;
        }

        Inscricao novaInscricao = new Inscricao();
        novaInscricao.setMembroId(event.membroId());
        novaInscricao.setPlano(plano);
        novaInscricao.setDataInicio(LocalDate.now());

        LocalDate dataFim = LocalDate.now().plusMonths(plano.getDuracaoEmMeses());
        novaInscricao.setDataFim(dataFim);
        novaInscricao.setAtiva(true);

        inscricaoRepository.save(novaInscricao);

        System.out.println("SUCESSO: Inscrição para Membro ID " + event.membroId() + " salva no DB do Plan-API.");
    }
}