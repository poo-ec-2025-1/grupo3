/**
 * Escreva uma descrição da classe Intervalo aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Intervalo
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
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
