package br.com.itau.geradornotafiscal.model.enums;


import java.util.Arrays;

public enum Regiao {

    NORTE(1.08),
    NORDESTE(1.085),
    CENTRO_OESTE(1.07),
    SUDESTE(1.048),
    SUL(1.06);

    public final double fretePercentual;

    Regiao(double fretePercentual) {
        this.fretePercentual = fretePercentual;
    }

    public static double calculaFreteComPercentual(double valorFrete, Regiao regiao) {
        return Arrays.stream(Regiao.values())
                .filter(reg -> reg.equals(regiao))
                .map(reg -> reg.fretePercentual * valorFrete)
                .findFirst()
                .orElse(0.0);
    }

}