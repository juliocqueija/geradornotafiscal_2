package br.com.itau.calculadoratributos.service;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.service.impl.FinanceiroServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class FinanceiroServiceImplTests {

    @InjectMocks
    private FinanceiroServiceImpl financeiroService;

    @DisplayName("Enviar nota fiscal para contas a receber com sucesso")
    @Test
    public void enviarNotaFiscalParaContasReceberOk() {
        NotaFiscal notaFiscal = NotaFiscal.builder().build();
        assertDoesNotThrow(() -> financeiroService.enviarNotaFiscalParaContasReceber(notaFiscal));
    }

}
