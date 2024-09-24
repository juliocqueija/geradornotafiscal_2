package br.com.itau.calculadoratributos.service;

import br.com.itau.geradornotafiscal.model.*;
import br.com.itau.geradornotafiscal.model.enums.Finalidade;
import br.com.itau.geradornotafiscal.model.enums.Regiao;
import br.com.itau.geradornotafiscal.service.impl.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeradorNotaFiscalServiceImplTests {

    @Mock
    private CalculaAliquotaProdutoServiceImpl calculaAliquotaProdutoService;

    @Mock
    private EstoqueServiceImpl estoqueService;

    @Mock
    private RegistroServiceImpl registroService;

    @Mock
    private EntregaServiceImpl entregaService;

    @Mock
    private FinanceiroServiceImpl financeiroService;

    @InjectMocks
    private GeradorNotaFiscalServiceImpl geradorNotaFiscalService;

    @DisplayName("Gerar nota fiscal para finalidade entrega")
    @Test
    public void gerarNotaFiscalEntrega() {
        Pedido pedido = Pedido
                .builder()
                .destinatario(
                        Destinatario
                                .builder()
                                .enderecos(
                                        List.of(
                                                Endereco
                                                        .builder()
                                                        .cep("00000000")
                                                        .logradouro("Rua street")
                                                        .numero("123")
                                                        .estado("liquido")
                                                        .complemento("algum")
                                                        .finalidade(Finalidade.ENTREGA)
                                                        .regiao(Regiao.SUDESTE)
                                                        .build()
                                        )
                                )
                                .build()
                )
                .build();

        when(calculaAliquotaProdutoService.calculaAliquota(any(Pedido.class))).thenReturn(
                List.of(
                        ItemNotaFiscal.builder().build()
                )
        );
        doNothing().when(estoqueService).enviarNotaFiscalParaBaixaEstoque(any(NotaFiscal.class));
        doNothing().when(registroService).registrarNotaFiscal(any(NotaFiscal.class));
        doNothing().when(entregaService).agendarEntrega(any(NotaFiscal.class));
        doNothing().when(financeiroService).enviarNotaFiscalParaContasReceber(any(NotaFiscal.class));

        NotaFiscal result = geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getItens().size()).isEqualTo(1)
        );
    }

    @DisplayName("Gerar nota fiscal para finalidade cobranca entrega")
    @Test
    public void gerarNotaFiscalCobrancaEntrega() {
        Pedido pedido = Pedido
                .builder()
                .destinatario(
                        Destinatario
                                .builder()
                                .enderecos(
                                        List.of(
                                                Endereco
                                                        .builder()
                                                        .finalidade(Finalidade.COBRANCA_ENTREGA)
                                                        .regiao(Regiao.SUDESTE)
                                                        .build()
                                        )
                                )
                                .build()
                )
                .build();

        when(calculaAliquotaProdutoService.calculaAliquota(any(Pedido.class))).thenReturn(
                List.of(
                        ItemNotaFiscal.builder().build()
                )
        );
        doNothing().when(estoqueService).enviarNotaFiscalParaBaixaEstoque(any(NotaFiscal.class));
        doNothing().when(registroService).registrarNotaFiscal(any(NotaFiscal.class));
        doNothing().when(entregaService).agendarEntrega(any(NotaFiscal.class));
        doNothing().when(financeiroService).enviarNotaFiscalParaContasReceber(any(NotaFiscal.class));

        NotaFiscal result = geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getItens().size()).isEqualTo(1)
        );
    }

}
