package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.model.enums.AliquotaValorTotalLimites;
import br.com.itau.geradornotafiscal.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.model.enums.TipoPessoa;
import br.com.itau.geradornotafiscal.service.CalculaAliquotaProdutoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculaAliquotaProdutoServiceImpl implements CalculaAliquotaProdutoService {

    public List<ItemNotaFiscal> calculaAliquota(Pedido pedido) {
        Destinatario destinatario = pedido.getDestinatario();
        TipoPessoa tipoPessoa = destinatario.getTipoPessoa();
        double aliquota = this.getAliquotaPercentual(
                tipoPessoa,
                destinatario.getRegimeTributacao(),
                pedido.getValorTotalItens()
        );

        return pedido.getItens()
                .stream()
                .map(item ->
                        ItemNotaFiscal
                                .builder()
                                .idItem(item.getIdItem())
                                .descricao(item.getDescricao())
                                .valorUnitario(item.getValorUnitario())
                                .quantidade(item.getQuantidade())
                                .valorTributoItem(item.getValorUnitario() * aliquota)
                                .build()
                )
                .collect(Collectors.toList());
    }

    private double getAliquotaPercentual(
            TipoPessoa tipoPessoa,
            RegimeTributacaoPJ regimeTributacaoPJ,
            double valorTotalItens
    ) {
        double aliquota = 0;

        switch (tipoPessoa) {
            case FISICA:
                aliquota = this.getAliquotaPF(valorTotalItens);
                break;
            case JURIDICA:
                aliquota = this.getAliquotaPJ(regimeTributacaoPJ, valorTotalItens);
                break;
        }

        return aliquota;
    }

    private double getAliquotaPF(double valorTotalItens) {
        Map<Double, Double> aliquotaPFMap = Map.of(
                AliquotaValorTotalLimites._500.valor - 1, 0.0, //-1 on key so we can use <= on all comparisons
                AliquotaValorTotalLimites._2000.valor, 0.12,
                AliquotaValorTotalLimites._3500.valor, 0.15,
                Double.MAX_VALUE, 0.17
        );

        return aliquotaPFMap
                .entrySet()
                .stream()
                .filter(entry -> valorTotalItens <= entry.getKey())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0.0);
    }

    private double getAliquotaPJ(RegimeTributacaoPJ regimeTributacaoPJ, double valorTotalItens) {
        return RegimeTributacaoPJ.getAliquotaByRegimeTributacaoPJAndValorTotal(
                regimeTributacaoPJ,
                valorTotalItens
        );
    }

}



