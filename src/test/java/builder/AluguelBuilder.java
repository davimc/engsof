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
    public AluguelBuilder comPagamento(LocalDate data){
        aluguel.setValorPago(LocacaoBuilder.umaLocacao().constroi().getValorAluguel());
        aluguel.setDataPagamento(data);

        return this;
    }
    public Aluguel constroi(){
        return aluguel;
    }
}
