public abstract class MaquinaLavar
{
    /**
     * Os atributos estáticos da classe simula um banco de dados
     * Obs.: passageiro uma vez que haverá cenário nos quais a máquina estará quebrada,
     *       ou caso ocorra um cadastro errado da máquina, criando um objeto que não reflita a realidade.
     *       Diversos casos no qual demonstram o defeito da máquina mecher no banco de dados. 
     */
    static int qtdMaquina = 0;
    static MaquinaLavar maquinasOfertadas[] = new MaquinaLavar[100];
    static double[] tempoOfertadoMaquinaS = new double[7];
    
    protected String idMaquina;
    protected int pesoMax;
    protected char tipoMaquina;
    protected double tempoOfertadoDaMaquina[] = Agenda.tempoDeFuncionamentoSemana.clone();
    
    MaquinaLavar()
    {
        maquinasOfertadas[qtdMaquina] = this;
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoMaquinaS.length; i++)
        {
            tempoOfertadoMaquinaS[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
    
    abstract void descontarTempo(int dia, double tempoReservado);
}
