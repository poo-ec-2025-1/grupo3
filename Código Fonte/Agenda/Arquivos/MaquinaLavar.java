public abstract class MaquinaLavar
{
    /**
     * Os atributos estáticos da classe simula um banco de dados
     */
    static int qtdMaquina = 0;
    static MaquinaLavar maquinasOfertadas[] = new MaquinaLavar[100];
    static double[] tempoOfertadoMaquinaS = new double[7];
    
    protected String idMaquina;
    protected int pesoMax;
    protected char tipoMaquina;
    protected double tempoOfertadoDaMaquina[] = Agenda.tempoDeFuncionamentoSemana;
    
    MaquinaLavar()
    {
        maquinasOfertadas[qtdMaquina] = this;
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoMaquinaS.length; i++)
        {
            tempoOfertadoMaquinaS[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
}
