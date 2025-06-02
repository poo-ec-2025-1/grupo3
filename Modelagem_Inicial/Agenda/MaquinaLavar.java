public class MaquinaLavar
{
    /**
     * Os atributos estáticos da classe fará parte do banco de dados
     */
    static int qtdMaquina = 0;
    static MaquinaLavar maquinasOfertadas[] = new MaquinaLavar[100];
    static double[] tempoDisponivelTotal = new double[7];
    
    protected double tempoDisponivelDaMaquina[] = Agenda.tempoDeFuncionamentoSemana;
    
    MaquinaLavar()
    {
        maquinasOfertadas[qtdMaquina] = this;
        qtdMaquina++;
        for (int i = 0; i < tempoDisponivelTotal.length; i++)
        {
            tempoDisponivelTotal[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
}
