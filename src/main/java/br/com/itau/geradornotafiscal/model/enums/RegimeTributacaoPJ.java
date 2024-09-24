package br.com.itau.geradornotafiscal.model.enums;

import java.util.Map;

public enum RegimeTributacaoPJ {

    SIMPLES_NACIONAL(
            Map.of(
                    AliquotaValorTotalLimites._1000.valor - 1.0, 0.03,  //-1 on key so we can use <= on all comparisons
                    AliquotaValorTotalLimites._2000.valor, 0.07,
                    AliquotaValorTotalLimites._5000.valor, 0.13,
                    Double.MAX_VALUE, 0.19

            )
    ),
    LUCRO_REAL(
            Map.of(
                    AliquotaValorTotalLimites._1000.valor - 1.0, 0.03,  //-1 on key so we can use <= on all comparisons
                    AliquotaValorTotalLimites._2000.valor, 0.09,
                    AliquotaValorTotalLimites._5000.valor, 0.15,
                    Double.MAX_VALUE, 0.20

            )
    ),
    LUCRO_PRESUMIDO(
            Map.of(
                    AliquotaValorTotalLimites._1000.valor - 1.0, 0.03,  //-1 on key so we can use <= on all comparisons
                    AliquotaValorTotalLimites._2000.valor, 0.09,
                    AliquotaValorTotalLimites._5000.valor, 0.16,
                    Double.MAX_VALUE, 0.20

            )
    ),
    OUTROS(
            Map.of(
                    AliquotaValorTotalLimites._1000.valor - 1.0, 0.0,  //-1 on key so we can use <= on all comparisons
                    AliquotaValorTotalLimites._2000.valor, 0.0,
                    AliquotaValorTotalLimites._5000.valor, 0.0,
                    Double.MAX_VALUE, 0.0

            )
    );

    public final Map<Double, Double> aliquotaMap;

    RegimeTributacaoPJ(Map<Double, Double> aliquotaMap) {
        this.aliquotaMap = aliquotaMap;
    }

    public static double getAliquotaByRegimeTributacaoPJAndValorTotal(
            RegimeTributacaoPJ regimeTributacaoPJ,
            double valorTotal
    ) {
        return regimeTributacaoPJ.aliquotaMap
                .entrySet()
                .stream()
                .filter(entry -> valorTotal <= entry.getKey())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0.0);
    }

}