public class Agenda
{
    // Atributos de classe
    static final double tempoDeFuncionamentoSemana[] = {8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0};
    static HorarioMarcado horariosMarcados[][] = new HorarioMarcado[7][999];     //"" Banco de Dados "" 
    
    // Atributos
    String nomeAgenda;
    //protected double tempoOfertadoAgenda[] = tempoDeFuncionamentoSemana;       //Em geral, esse será o padrão
    protected double tempoOfertadoAgenda[] = {0.0, 1.0, 0.5, 2.0, 7.0, 8.0, 4.0};      //Esse é só um teste de uma instaciacao no qual o domingo é retirado da oferta por exemplo. Talvez só alguns clientes tem acesso lavanderia no domingo...
    final double tempoPadrao = 1.0;
    
    //Classes
    class HorarioMarcado extends Intervalo
    {
        int matriculaDoUsuario;
        int dia;
        String idMaquina;
        
        HorarioMarcado(int dia, double inicioDoHorario, double fimDoHorario, int matricula)
        {
            super(inicioDoHorario, fimDoHorario);
            this.dia = dia;
            matriculaDoUsuario = matricula;
        }
        
        boolean selecionarDia()
        {
            if (dia < tempoDeFuncionamentoSemana.length && tempoDeFuncionamentoSemana[dia] >= 0)
            {
                if(tempoDeFuncionamentoSemana[dia] != 0)
                {
                    if(tempoOfertadoAgenda[dia] != 0)
                    {
                        return true;                
                    }
                    else
                    {
                        System.out.println("O dia " + dia  + " selecionado está cheio...");
                    }
                    
                }
                else
                {
                    System.out.println("O dia " + dia  + " selecionado está indisponível...");
                }
                
            }
            else
            {
                System.out.println("Não é possível selecionar esse dia " + dia + "...");
            }
            return false;
        }
        
        boolean indisponibilizarTempo()     //Talvez mude esse nome...
        {
            if( this.selecionarDia() )
            {
                    if(tempoTotal <= tempoOfertadoAgenda[this.dia])
                    /**
                     * É impossível indisponibilzar algo já indisponível
                     */
                    {
                        tempoOfertadoAgenda[this.dia] -= this.tempoTotal;
                        System.out.println("Horário marcado para o dia " + dia + " :)");
                        return true;
                    }
                    else
                    {
                        System.out.println("O dia " + dia + " requerido não tem essa disponibilidade de tempo em específico...");
                    }
            }
            return false;
        }
    }
    
    /**
     * Construtor para objetos da classe Agenda
     */
    Agenda()
    {
        setNomeAgenda("Migué");
    }
    
    Agenda(String nome)
    {
        setNomeAgenda(nome);
    }
    
    public boolean marcarHorario(int dia, double inicioDoHorario, double fimDoHorario, int matricula, double pesoRoupa)
    {
        HorarioMarcado novo = new HorarioMarcado(dia, inicioDoHorario, fimDoHorario, matricula);
        if (novo.indisponibilizarTempo())
        {
            
            for (int i = 0; i < horariosMarcados[dia].length; i++)
            {
                
                if (horariosMarcados[dia][i] == null)
                {
                    horariosMarcados[dia][i] = novo;
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean marcarHorario(int dia, double inicioDoHorario, double fimDoHorario, int matricula, MaquinaLavar tipoDaMaquina)
    {
        return false;
    }
    
    public boolean marcarHorario(int dia, double inicioDoHorario, double fimDoHorario, int matricula, char tipoDaMaquina)
    {
        return false;
    }
    
    /*public ?UmObjeto? gerarCalendario()
    {
        return calendario;
    }*/
    
    void setNomeAgenda(String nome)
    {
        nomeAgenda = "Agenda de " + nome;
    }
}
