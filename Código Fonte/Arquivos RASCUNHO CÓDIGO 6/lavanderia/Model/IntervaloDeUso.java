package lavanderia.Model;

import java.time.LocalDateTime;
import java.util.Date;
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

@DatabaseTable(tableName = "intervalo de uso")
public class IntervaloDeUso extends Intervalo
{
    
    @DatabaseField(generatedId = true, dataType = DataType.INTEGER)
    private int id;
    
    @DatabaseField(dataType = DataType.STRING)
    protected String inicio;
    
    @DatabaseField(dataType = DataType.STRING)
    protected String fim;
    
    @DatabaseField(dataType = DataType.STRING)
    protected String status = null;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    protected Aparelho aparelho;
    
    public IntervaloDeUso()
    {
        
    }
    
    IntervaloDeUso(LocalDateTime inicio, LocalDateTime fim)
    {
        super(inicio, fim);
        this.inicio = super.inicio;
        this.fim = super.fim;
    }
    
    public IntervaloDeUso(Aparelho aparelho, LocalDateTime inicio, LocalDateTime fim)
    {
        this(inicio, fim);
        if (aparelho == null)
                throw new IllegalArgumentException("Aparelho não pode ser nulo.");
                
        this.aparelho = aparelho;
    }
    
    Aparelho getAparelho()
    {
        return aparelho;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getIntervaloHorario() {
        return ""+this.inicio+"-"+this.fim+"";
    }
    
    int getId()
    {
        return id;
    }
}
