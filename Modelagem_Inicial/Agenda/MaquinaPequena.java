public class MaquinaPequena extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoDisponivelTotal = new double[7];
    
    public MaquinaPequena()
    {
        qtdMaquina++;
        for (int i = 0; i < tempoDisponivelTotal.length; i++)
        {
            tempoDisponivelTotal[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }

}
