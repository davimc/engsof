package repositories;

import models.Cliente;
import models.Locacao;

public interface EmailService {

    public void notificaAtraso(Cliente cliente);

}
