public class MaquinaPesada extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoOfertadoMaquinaS = new double[7];
    
    public MaquinaPesada(String idMaquina)
    {
        this.idMaquina = idMaquina;
        pesoMax = 15;
        tipoMaquina = 'p';
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoMaquinaS.length; i++)
        {
            tempoOfertadoMaquinaS[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
    
    @Override
    void descontarTempo(int dia, double tempoReservado)
    /*
     * Do menos ao mais geral
     */
    {
        this.tempoOfertadoDaMaquina[dia] -= tempoReservado;
        MaquinaPesada.tempoOfertadoMaquinaS[dia] -= tempoReservado;
        MaquinaLavar.tempoOfertadoMaquinaS[dia] -= tempoReservado;
    }
}
