package lavanderia.Model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

/**
 * Estará presente no database.
 * Irá compor a reserva, portanto fará parte do banco de dados.
 * 
 * @author Miguel
 */

@DatabaseTable(tableName = "Intervalo de Uso")
public class IntervaloDeUso extends Intervalo
{
    
    @DatabaseField(generatedId = true, dataType = DataType.INTEGER)
    private int id;
    
    @DatabaseField(dataType = DataType.STRING)
    protected String dia;
    
    @DatabaseField(dataType = DataType.STRING)
    protected String inicio;
    
    @DatabaseField(dataType = DataType.STRING)
    protected String fim;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    protected Aparelho aparelho;
    
    public IntervaloDeUso()
    {
        
    }
    
    IntervaloDeUso(LocalTime inicio, LocalTime fim)
    {
        super(inicio, fim);
        
        DateTimeFormatter formatadorDeHora = DateTimeFormatter.ofPattern(Intervalo.formatoTime);
        this.inicio = inicio.format(formatadorDeHora);
        this.fim = fim.format(formatadorDeHora);
    }
    
    IntervaloDeUso(LocalTime inicio, LocalTime fim, LocalDate dia)
    {
        super(inicio, fim, dia);
                
        DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern(Intervalo.formatoDate);
        DateTimeFormatter formatadorDeHora = DateTimeFormatter.ofPattern(Intervalo.formatoTime);
        this.dia = dia.format(formatadorDeData);
        this.inicio = inicio.format(formatadorDeHora);
        this.fim = fim.format(formatadorDeHora);
    }
    
    IntervaloDeUso(LocalTime inicio, LocalTime fim, LocalDate dia, Aparelho aparelho)
    {
        this(inicio, fim, dia);
        if (aparelho == null)
                throw new IllegalArgumentException("Aparelho não pode ser nulo.");
                
        this.aparelho = aparelho;    
    }
    
    Aparelho getAparelho()
    {
        return aparelho;
    }
    
    int getId()
    {
        return id;
    }
    
    public String getDia() {
        return dia;
    }

    public String getInicio() {
        return inicio;
    }
    
    public String getFim() {
        return fim;
    }

}
