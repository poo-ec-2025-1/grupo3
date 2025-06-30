package lavanderia.Model;

/**
 * Estará presente no database
 * Será uma composicao do Usuario, Intervalo e DiaDaReserva
 * A composicao será feita na Agenda e, a partir dela será mandada ao database
 * Assim, tudo necessário para o armazenamento de horários no Database será organizado em um só objeto, facilitando a análise.
 * 
 * @author Miguel Moreira
 */

import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

@DatabaseTable(tableName = "reserva")
public class Reserva
{
    @DatabaseField(generatedId = true, dataType = DataType.INTEGER)
    private int id;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private IntervaloDeUso intervalo;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Usuario user;
    
    /**
     * Construtor para objetos da classe Reserva
     */
    public Reserva()
    {
        
    }
    
    Reserva(Usuario user, IntervaloDeUso intervalo)
    {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }
        if (intervalo == null) {
            throw new IllegalArgumentException("Intervalo de uso não pode ser nulo.");
        }
        this.user = user;
        this.intervalo = intervalo;
    }
    
    Usuario getUser()
    {
        return user;
    }
    
    int getId()
    {
        return id;
    }
    
    IntervaloDeUso getIntervalo()
    {
        return intervalo;
    }
}
