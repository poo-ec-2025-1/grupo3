/**
 * Servirá como uma unidade de tempo. 
 * De maneira que possibilite uma lógica para agrupar os "HoráriosMarcados" em grandes "Intervalos" no banco de dados futuramente.
 * 
 * @author Miguel Moreira 
 */
public class Intervalo
{
    // variáveis de instância
    //protected int dia;  Talvez não seja necessário
    protected double inicio = 0;
    protected double fim = 0;
    protected double tempoTotal = 0;
    protected String idMaquina;

    /**
     * Construtor para objetos da classe Intervalo
     */
    protected Intervalo(){}
    
    public Intervalo(double inicio, double fim)
    {
        // inicializa variáveis de instância
        boolean teste = (inicio < fim);
        if (teste)  {
                this.inicio = inicio;
                this.fim = fim;
                tempoTotal = fim - inicio;
        }
    }
    
    public Intervalo(String idMaquina, double inicio, double fim)
    {
        this(inicio, fim);
        this.idMaquina = idMaquina;
    }
}
