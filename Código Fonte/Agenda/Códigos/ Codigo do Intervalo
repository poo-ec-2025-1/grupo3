/**
 * Servirá como uma unidade de tempo. 
 * De maneira que possibilite uma lógica para agrupar os "HoráriosMarcados" em grandes "Intervalos" no banco de dados futuramente.
 * 
 * @author Miguel Moreira 
 */
public class Intervalo
{
    // variáveis de instância
    protected int dia;
    protected double inicio = 0;
    protected double fim = 0;
    protected double intervaloDeTempo = 0;
    protected String idMaquina;

    /**
     * Construtor para objetos da classe Intervalo
     */
    protected Intervalo(){}
    
    public Intervalo(int dia, double inicio, double fim)
    {
        
        // inicializa variáveis de instância
        boolean teste = (inicio < fim);
        if (teste)  {
                this.dia = dia;
                this.inicio = inicio;
                this.fim = fim;
                intervaloDeTempo = fim - inicio;
        }
    }
    
    public Intervalo(String idMaquina, int dia,double inicio, double fim)
    {
        this(dia, inicio, fim);
        this.idMaquina = idMaquina;
    }
}
