public class MaquinaPesada extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoDisponivelTotal = new double[7];
    
    public MaquinaPesada()
    {
        qtdMaquina++;
        for (int i = 0; i < tempoDisponivelTotal.length; i++)
        {
            tempoDisponivelTotal[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }

}
