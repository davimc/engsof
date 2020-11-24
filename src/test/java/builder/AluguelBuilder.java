package builder;

import models.Aluguel;
import models.Locacao;

import java.time.LocalDate;

public class AluguelBuilder {
    private Aluguel aluguel;

    public AluguelBuilder(){

    }
    public static AluguelBuilder umAluguel(){
        AluguelBuilder builder = new AluguelBuilder();
        builder.aluguel = new Aluguel();

        builder.aluguel.setLocacao(LocacaoBuilder.umaLocacao().constroi());
        builder.aluguel.setDataVencimento(LocalDate.now().plusMonths(1));
        builder.aluguel.setDataPagamento(null);
        builder.aluguel.setValorPago(0.0);
        builder.aluguel.setObs("");

        return builder;
    }
    public AluguelBuilder comLocacao(Locacao locacao){
        aluguel.setLocacao(locacao);
        return this;
    }
    public AluguelBuilder comUmMesAtraso(){
        aluguel.setDataVencimento(LocalDate.now().minusMonths(1));

        return this;
    }
    public AluguelBuilder comPagamento(){
        aluguel.setValorPago(LocacaoBuilder.umaLocacao().constroi().getValorAluguel());
        aluguel.setDataPagamento(LocalDate.now());

        return this;
    }
    public Aluguel constroi(){
        return aluguel;
    }
}
