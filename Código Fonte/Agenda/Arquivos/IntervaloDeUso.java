import java.time.LocalDateTime;
import java.lang.Exception;

/**
 * Estará presente no database
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
    
public class IntervaloDeUso extends Intervalo
{
    static public enum Status 
    {
        EM_USO, RESERVADO, EM_MANUTENCAO, INDISPONIVEL;
    }
    protected Aparelho aparelho;
    protected Status status = null;
    
    IntervaloDeUso()
    {
        
    }
    
    IntervaloDeUso(LocalDateTime inicio, LocalDateTime fim, Aparelho aparelho) throws Exception
    {
        super(inicio, fim);
        if (aparelho == null)
        {
            new Exception("É necessário um aparelho.");
        }
        this.aparelho = aparelho;
    }
}
