package br.com.itau.geradornotafiscal.model.enums;

public enum AliquotaValorTotalLimites {

    _500(500.00),
    _1000(1000.00),
    _2000(2000.00),
    _3500(3500.00),
    _5000(5000.00);

    public final double valor;

    AliquotaValorTotalLimites(double valor) {
        this.valor = valor;
    }

}
