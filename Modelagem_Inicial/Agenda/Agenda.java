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
        
        double[] definirOfertaDasMaquinas(char tipoMaquina)
        {
                double tempos[] = new double[7];
                switch(tipoMaquina)
                {
                    case 'l':
                    case 'L':
                        tempos = MaquinaPequena.tempoOfertadoMaquinaS;
                        break;
                        
                    case 'm':
                    case 'M':
                        tempos = MaquinaMedia.tempoOfertadoMaquinaS;
                        break;
                        
                    case 'p':
                    case 'P':
                    case 'g':
                    case 'G':
                        tempos = MaquinaPesada.tempoOfertadoMaquinaS;
                        break;
                        
                    case 't':
                    case 'T':
                        tempos = MaquinaLavar.tempoOfertadoMaquinaS;
                        break;
                }
                return tempos;
        }
            
        boolean selecionarDia(char tipoMaquina)
        {
            if (dia < tempoDeFuncionamentoSemana.length && tempoDeFuncionamentoSemana[dia] >= 0)
            {
                if(tempoDeFuncionamentoSemana[dia] != 0)
                {
                    if(definirOfertaDasMaquinas(tipoMaquina)[dia] != 0)
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
        
        boolean indisponibilizarTempo(char tipoMaquina)     //Talvez mude esse nome...
        {
            if( selecionarDia(tipoMaquina) )
            {
                for(int i = 0/* i = 100*/; i < MaquinaLavar.maquinasOfertadas.length && MaquinaLavar.maquinasOfertadas[i] != null; i++)
                {
                    if(MaquinaLavar.maquinasOfertadas[i].tipoMaquina == tipoMaquina)
                    {
                        if(tempoTotal <= MaquinaLavar.maquinasOfertadas[i].tempoOfertadoDaMaquina[this.dia])
                        /**
                         * É impossível indisponibilzar algo já indisponível
                         */
                        {
                            MaquinaLavar.maquinasOfertadas[i].tempoOfertadoDaMaquina[this.dia] -= this.tempoTotal;
                            idMaquina = MaquinaLavar.maquinasOfertadas[i].idMaquina;
                            System.out.println("Horário marcado para o dia " + dia + " :)");
                            return true;
                        }
                    }
                } 
                System.out.println("O dia " + dia + " requerido não tem essa disponibilidade de tempo em específico...");
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
        char tipo = ' ';
        if (pesoRoupa < 5)
        {
            tipo = 'l';
        }
        else if(pesoRoupa < 8)
        {
            tipo = 'm';
        }
        else if(pesoRoupa < 15)
        {
            tipo = 'p';
        }
        
        if (novo.indisponibilizarTempo(tipo))
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
