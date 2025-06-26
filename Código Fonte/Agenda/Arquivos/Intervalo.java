/**
 * Servirá como uma unidade de tempo. 
 * De maneira que possibilite uma lógica para agrupar os "HoráriosIntervalo" em grandes "Intervalos" no banco de dados futuramente.
 * 
 * @author Miguel Moreira 
 */

import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.time.LocalDate;

public class Intervalo
{
    // variáveis de instância
    protected LocalDateTime inicio;
    protected LocalDateTime fim;

    /**
     * Construtor para objetos da classe Intervalo
     */
    protected Intervalo()
    {
        
    }
    
    public Intervalo(LocalDateTime inicio, LocalDateTime fim) throws DateTimeException
    {
        
        // inicializa variáveis de instância
        boolean teste = fim.isAfter(inicio);
        if(fim.isAfter(inicio))
        {
            this.fim = fim;
            this.inicio = inicio;
        }
        else
        {
            DateTimeException e = new DateTimeException("Horário final é anterior ao inicial.");
            throw e;
        }
    }
}
