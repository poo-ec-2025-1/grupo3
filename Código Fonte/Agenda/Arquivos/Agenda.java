import java.time.LocalDateTime;
import java.lang.Exception;
import java.lang.StringBuilder;

/**
 * Escreva uma descrição da classe Agenda aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Agenda
{
    static final String[] diasSemana = {"domingo", "segunda", "terça", "quarta", "quinta", "sexta", "sábado"};
    
    private double tempoDeFuncionamentoSemana[] = new double[7];
    
    class IntervaloReservavel extends IntervaloDeUso
    {
       IntervaloReservavel(LocalDateTime inicio, LocalDateTime fim, Aparelho aparelho) throws Exception
       {
           super(inicio, fim, aparelho);
       }
    }
    
    Agenda()
    {
        for (int i = 0; i < tempoDeFuncionamentoSemana.length; i++)
        {
            tempoDeFuncionamentoSemana[i] = 8.0;
        }
    }
    
    Agenda(double tempo) throws Exception
    {
        if (tempo < 0.0 || tempo > 24.0) {
            throw new Exception("Tempo padrão inválido: " + tempo + "h. Deve estar entre 0.0 e 24.0 horas.");
        }
    
        tempoDeFuncionamentoSemana = new double[7];
        for (int i = 0; i < tempoDeFuncionamentoSemana.length; i++) {
            tempoDeFuncionamentoSemana[i] = tempo;
        }
    }
    
    Agenda(double dom, double seg, double ter, double qua, double qui, double sex, double sab) throws Exception
    {
        double[] novosTempos = {dom, seg, ter, qua, qui, sex, sab};
        StringBuilder erros = new StringBuilder();
        
        for (int i = 0; i < novosTempos.length; i++)
        {
            if (novosTempos[i] < 0.0 || novosTempos[i] > 24.0) {
            erros.append(" - ")
                 .append(diasSemana[i])
                 .append(": ")
                 .append(novosTempos[i])
                 .append(" horas (deve estar entre 0.0 e 24.0)\n");
        }
    }

    if (erros.length() > 0) {
        throw new Exception("Foram encontrados os seguintes erros nos tempos de funcionamento:\n" + erros.toString());
    }

    tempoDeFuncionamentoSemana = novosTempos.clone();
    }
    
    public double[] getTempoDeFuncionamentoSemana()
    {
        return tempoDeFuncionamentoSemana.clone();
    }
}
