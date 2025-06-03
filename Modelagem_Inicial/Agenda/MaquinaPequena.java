public class MaquinaPequena extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoOfertadoMaquinaS = new double[7];
    
    public MaquinaPequena(String idMaquina)
    {
        this.idMaquina = idMaquina;
        pesoMax = 5;
        tipoMaquina = 'l';
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoMaquinaS.length; i++)
        {
            tempoOfertadoMaquinaS[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
    
    MaquinaPequena()
    {
        this("migué");
        double teste[] = {0.0, 1.0, 0.5, 2.0, 7.0, 8.0, 4.0};
        tempoOfertadoDaMaquina = teste;
        tempoOfertadoMaquinaS = teste;
    }

}
