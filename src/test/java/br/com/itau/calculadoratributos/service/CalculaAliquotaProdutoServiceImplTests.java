package br.com.itau.calculadoratributos.service;

import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.Item;
import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.model.enums.TipoPessoa;
import br.com.itau.geradornotafiscal.service.impl.CalculaAliquotaProdutoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class CalculaAliquotaProdutoServiceImplTests {

    @InjectMocks
    private CalculaAliquotaProdutoServiceImpl calculaAliquotaProdutoService;

    @DisplayName("Calcula aliquota pessoa fisica")
    @Test
    public void calculaAliquotaPF() {
        Pedido pedido = Pedido
                .builder()
                .valorTotalItens(400.0)
                .itens(
                        List.of(
                                new Item(1L, "item", 40.0, 10)
                        )
                )
                .destinatario(
                        Destinatario
                                .builder()
                                .tipoPessoa(TipoPessoa.FISICA)
                                .regimeTributacao(RegimeTributacaoPJ.OUTROS)
                                .build()
                )
                .build();

        List<ItemNotaFiscal> result = calculaAliquotaProdutoService.calculaAliquota(pedido);

        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.size()).isEqualTo(1)
        );
    }

    @DisplayName("Calcula aliquota pessoa juridica")
    @Test
    public void calculaAliquotaPJ() {
        Pedido pedido = Pedido
                .builder()
                .valorTotalItens(900.0)
                .itens(
                        List.of(
                                new Item(1L, "item 1", 40.0, 10),
                                new Item(2L, "item 2", 50.0, 10)
                        )
                )
                .destinatario(
                        Destinatario
                                .builder()
                                .tipoPessoa(TipoPessoa.JURIDICA)
                                .regimeTributacao(RegimeTributacaoPJ.SIMPLES_NACIONAL)
                                .build()
                )
                .build();

        List<ItemNotaFiscal> result = calculaAliquotaProdutoService.calculaAliquota(pedido);

        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.size()).isEqualTo(2)
        );

    }

}
