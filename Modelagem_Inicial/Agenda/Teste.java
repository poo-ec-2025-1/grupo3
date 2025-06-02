public class Teste
{
    public static void main(String args[])
    {
        Agenda agenda = new Agenda();
        
        agenda.marcarHorario(9, 10, 11, 123, '1');
        agenda.marcarHorario(0, 10, 11, 123, '1');
        agenda.marcarHorario(1, 10, 11, 123, '1');
        
        agenda.marcarHorario(1, 10, 11, 789, '1');
        agenda.marcarHorario(2, 10, 11, 789, '1');
        agenda.marcarHorario(3, 10, 11, 789, '1');
    }
}
