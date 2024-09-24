package br.com.itau.calculadoratributos.port.out;

import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.port.out.EntregaIntegrationPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class EntregaIntergrationPortTests {

    @InjectMocks
    private EntregaIntegrationPort entregaIntegrationPort;

    @DisplayName("Criar agendamento entrega pedido com menos de 5 itens")
    @Test
    public void criarAgendamentoEntregaMenorQueCinco() {
        NotaFiscal notaFiscal = NotaFiscal
                .builder()
                .itens(
                        List.of(
                                ItemNotaFiscal.builder().build()
                        )
                )
                .build();

        assertDoesNotThrow(() -> entregaIntegrationPort.criarAgendamentoEntrega(notaFiscal));
    }

    @DisplayName("Criar agendamento entrega pedido com mais de 5 itens")
    @Test
    public void criarAgendamentoEntregaMaiorQueCinco() {
        NotaFiscal notaFiscal = NotaFiscal
                .builder()
                .itens(
                        List.of(
                                ItemNotaFiscal.builder().build(),
                                ItemNotaFiscal.builder().build(),
                                ItemNotaFiscal.builder().build(),
                                ItemNotaFiscal.builder().build(),
                                ItemNotaFiscal.builder().build(),
                                ItemNotaFiscal.builder().build()
                        )
                )
                .build();

        assertDoesNotThrow(() -> entregaIntegrationPort.criarAgendamentoEntrega(notaFiscal));
    }

}
