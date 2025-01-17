package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.port.out.EntregaIntegrationPort;
import br.com.itau.geradornotafiscal.service.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EntregaIntegrationPort entregaIntegrationPort;

    @Override
    public void agendarEntrega(NotaFiscal notaFiscal) {
        try {
            //Simula o agendamento da entrega
            Thread.sleep(150);
            entregaIntegrationPort.criarAgendamentoEntrega(notaFiscal);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
