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
    static public enum Status 
    {
        EM_USO, RESERVADO, EM_MANUTENCAO, INDISPONIVEL;
    }
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    protected Aparelho aparelho;
    
    @DatabaseField
    protected Status status = null;
    
    IntervaloDeUso()
    {
        
    }
    
    IntervaloDeUso(LocalDateTime inicio, LocalDateTime fim, Aparelho aparelho) throws Exception
    {
        super(inicio, fim);
        if (aparelho == null)
        {
            throw new IllegalArgumentException("Aparelho não pode ser nulo.");
        }
        this.aparelho = aparelho;
    }
    
    int getId()
    {
        return this.id;
    }
}
