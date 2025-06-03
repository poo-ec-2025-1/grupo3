public class MaquinaPesada extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoOfertadoTotal = new double[7];
    
    public MaquinaPesada(String idMaquina)
    {
        this.idMaquina = idMaquina;
        pesoMax = 15;
        tipoMaquina = 'p';
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoTotal.length; i++)
        {
            tempoOfertadoTotal[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
}
