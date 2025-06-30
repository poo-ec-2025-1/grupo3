package lavanderia.Model;

/**
 * Servirá como uma unidade de tempo. 
 * De maneira que possibilite uma análise para calcular intervalos disponíveis/indisponíveis.
 * É herdado pelo IntervaloDeUso.
 * 
 * @author Miguel Moreira 
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

public class Intervalo 
{
    public final static String formatoData = "yyyy-MM-dd HH:mm";
    
    protected String inicio;
    
    protected String fim;

    /**
     * Construtor para objetos da classe Intervalo
     */
    public Intervalo()
    {
        
    }
    
    public Intervalo(LocalDateTime inicio, LocalDateTime fim)
    {
        
        //Por causa da data padrão
        inicio = inicio.minusNanos(inicio.getNano());
        inicio = inicio.minusSeconds(inicio.getSecond());
        fim = fim.minusNanos(fim.getNano());
        fim = fim.minusSeconds(fim.getSecond());
        
        boolean teste = fim.isAfter(inicio);
        if(fim.isAfter(inicio))
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatoData);
            this.inicio = inicio.format(formatter);
            this.fim = fim.format(formatter);
        }
        else
        {
            new IllegalArgumentException("Horário final é anterior ao inicial.");
        }
    }    
    public String getInicio()
    {
        return inicio;
    }
    
    public String getFim()
    {
        return fim;
    }
}
