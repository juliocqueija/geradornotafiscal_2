package br.com.itau.calculadoratributos.service;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.service.impl.EstoqueServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class EstoqueServiceImplTests {

    @InjectMocks
    private EstoqueServiceImpl estoqueService;

    @DisplayName("Enviar nota fiscal para baixa de estoque com sucesso")
    @Test
    public void enviarNotaFiscalParaBaixaEstoqueOk() {
        NotaFiscal notaFiscal = NotaFiscal.builder().build();
        assertDoesNotThrow(() -> estoqueService.enviarNotaFiscalParaBaixaEstoque(notaFiscal));
    }

}
