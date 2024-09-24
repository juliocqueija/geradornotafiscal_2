package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.*;
import br.com.itau.geradornotafiscal.model.enums.Finalidade;
import br.com.itau.geradornotafiscal.model.enums.Regiao;
import br.com.itau.geradornotafiscal.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeradorNotaFiscalServiceImpl implements GeradorNotaFiscalService {

    private final CalculaAliquotaProdutoService calculaAliquotaProdutoService;
    private final EstoqueService estoqueService;
    private final RegistroService registroService;
    private final EntregaService entregaService;
    private final FinanceiroService financeiroService;

    @Override
    public NotaFiscal gerarNotaFiscal(Pedido pedido) {
        List<ItemNotaFiscal> itemNotaFiscalList = calculaAliquotaProdutoService.calculaAliquota(pedido);
        Destinatario destinatario = pedido.getDestinatario();
        Regiao regiao = getRegiao(destinatario);

        String idNotaFiscal = UUID.randomUUID().toString();

        NotaFiscal notaFiscal = NotaFiscal.builder()
                .idNotaFiscal(idNotaFiscal)
                .data(LocalDateTime.now())
                .valorTotalItens(pedido.getValorTotalItens())
                .valorFrete(Regiao.calculaFreteComPercentual(pedido.getValorFrete(), regiao))
                .itens(itemNotaFiscalList)
                .destinatario(pedido.getDestinatario())
                .build();

        estoqueService.enviarNotaFiscalParaBaixaEstoque(notaFiscal);
        registroService.registrarNotaFiscal(notaFiscal);
        entregaService.agendarEntrega(notaFiscal);
        financeiroService.enviarNotaFiscalParaContasReceber(notaFiscal);

        return notaFiscal;
    }

    private static Regiao getRegiao(Destinatario destinatario) {
        return destinatario.getEnderecos().stream()
                .filter(endereco ->
                        endereco.getFinalidade() == Finalidade.ENTREGA ||
                                endereco.getFinalidade() == Finalidade.COBRANCA_ENTREGA
                )
                .map(Endereco::getRegiao)
                .findFirst()
                .orElse(null);
    }

}