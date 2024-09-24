package br.com.itau.calculadoratributos.service;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.service.impl.RegistroServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class RegistroServiceImplTests {

    @InjectMocks
    private RegistroServiceImpl registroService;

    @DisplayName("Registrar nota fiscal com sucesso")
    @Test
    public void registrarNotaFiscalOk() {
        NotaFiscal notaFiscal = NotaFiscal.builder().build();
        assertDoesNotThrow(() -> registroService.registrarNotaFiscal(notaFiscal));
    }

}
