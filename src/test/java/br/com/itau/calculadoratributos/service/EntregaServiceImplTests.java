package br.com.itau.calculadoratributos.service;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.port.out.EntregaIntegrationPort;
import br.com.itau.geradornotafiscal.service.impl.EntregaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class EntregaServiceImplTests {

    @Mock
    private EntregaIntegrationPort entregaIntegrationPort;

    @InjectMocks
    private EntregaServiceImpl entregaService;

    @DisplayName("Agendar entrega com sucesso")
    @Test
    public void agendarEntregaOk() {
        NotaFiscal notaFiscal = NotaFiscal.builder().build();
        doNothing().when(entregaIntegrationPort).criarAgendamentoEntrega(any(NotaFiscal.class));
        assertDoesNotThrow(() -> entregaService.agendarEntrega(notaFiscal));
    }

}
