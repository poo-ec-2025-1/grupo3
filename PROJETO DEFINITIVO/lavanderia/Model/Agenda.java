package lavanderia.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate; 

/**
 * Fará todas as checagens para garantir as reservas.
 * Focado a um servico com uma implementacao abstrata e facilitada para o cliente.
 * Necessita dos repositórios para funcionar.
 * 
 * @author Miguel Moreira 
 */

public class Agenda
{
    
    private static AparelhoRepository R_Aparelho;
    private static UsuarioRepository R_Usuario;
    private static ReservaRepository R_Reserva;
    private static IntervaloDeUsoRepository R_Intervalo;
    private static DiaDaReservaRepository R_Dia;
    
    
    private static double tempoDeFuncionamentoSemana[] = {24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0};
    static final String[] diasSemana = {"segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo"};
    
    
    
    /*
    public static Database testD = new Database("testD");
    private static Aparelho teste = new Aparelho("Eletrolux", "Gostosa", 10, true, 8000.0);
    private static LocalDateTime F_Teste = LocalDateTime.of(2025, 6, 30, 14, 30);
    private static LocalDateTime I_Teste = LocalDateTime.of(2025, 6, 30, 6, 17);
    public IntervaloReservavel horarioTeste = new IntervaloReservavel(I_Teste, F_Teste);
    public DiaReservavel diaTeste = new DiaReservavel(I_Teste, teste);
    */
   
    class DiaReservavel extends DiaDaReserva
    {
        DiaReservavel(LocalDateTime tempo, Aparelho aparelho)
        {
            super();
            ano = tempo.getYear();
            diaDoAno = tempo.getDayOfYear();
            diaDaSemana = tempo.getDayOfWeek().getValue() - 1;
            tempoDeFuncionamento = tempoDeFuncionamentoSemana[diaDaSemana];
            tempoDisponivel = tempoDeFuncionamento;
            this.aparelho = aparelho;
            if (diaDoAno == 0)
                throw new IllegalArgumentException("Dia do ano não pode ser zero.");

            if (diaDaSemana < 0 || diaDaSemana > 6)
                throw new IllegalArgumentException("Dia da semana deve estar entre 0 (domingo) e 6 (sábado).");

            if (tempoDeFuncionamento < 0 || tempoDeFuncionamento >= 24)
                throw new IllegalArgumentException("Tempo de funcionamento deve estar entre 0 e 24 horas.");
                
            if (aparelho == null)
                    throw new IllegalArgumentException("Aparelho não pode ser nulo.");
                    
        }
        
        Integer jaEstaReservado()
        {
            List<DiaDaReserva> dias = R_Dia.loadAll();
            if (dias == null || dias.isEmpty())
            {
                return null;
            }
            
            for(DiaDaReserva d : dias)
            {
                if(ano == d.getAno() && diaDoAno == d.getDiaDoAno() && aparelho.getId() == d.getAparelho().getId())
                {
                    return d.getIdDiaDaReserva();
                }
            }
            
            return null;
        }
        
        DiaDaReserva terDiaParaReserva()
        {            
            Integer reposta = jaEstaReservado();
            
            if (reposta == null)
            {
                R_Dia.create(this);
                return this;
            }
            
            return R_Dia.loadFromId(reposta.intValue());
        }
        
        public Reserva criarReservaEAtualizarTempo(Usuario usuario, Aparelho aparelho, LocalDate data, IntervaloHorario horario) {
        if (usuario == null || aparelho == null || data == null || horario == null) {
            // Verificação para garantir que todos os dados foram passados como parâmtro.
            System.err.println("Dados insuficientes para criar a reserva.");
            return null;
        }

        /* encontrar/criar o DiaDaReserva para este aparelho e data.
        *  a classe DiaReservavel já faz esse trabalho.
        */
        DiaReservavel diaReservavel = new DiaReservavel(data.atStartOfDay(), aparelho);
        DiaDaReserva diaParaReserva = diaReservavel.terDiaParaReserva();

        // calcular a duração do intervalo fornecido em horas (como double).
        Duration duracao = Duration.between(horario.getHoraInicio(), horario.getHoraFim());
        double duracaoEmHoras = duracao.toMinutes() / 60.0;

        // tentar indisponibilizar o tempo, o método já valida se há tempo suficiente.
        boolean sucessoAoAbaterTempo = diaParaReserva.indisponibilzarTempo(duracaoEmHoras);

        if (!sucessoAoAbaterTempo) {
            System.err.println("Falha ao fazer a reserva. Talvez não haja tempo suficiente.");
            return null; 
        }

        // se der certo, é feita a atualização no banco de dados.
        R_Dia.update(diaParaReserva);

        // criar o IntervaloDeUso que será utilizado.
        LocalDateTime inicio = data.atTime(horario.getHoraInicio());
        LocalDateTime fim = data.atTime(horario.getHoraFim());
        IntervaloDeUso intervaloDeUso = new IntervaloDeUso(aparelho, inicio, fim);
        intervaloDeUso.setStatus("RESERVADO");
        R_Intervalo.create(intervaloDeUso);

        // por fim, criar a Reserva e em seguida salvá-la no banco de dados.
        Reserva novaReserva = new Reserva(usuario, intervaloDeUso);
        R_Reserva.create(novaReserva);

        System.out.println("Reserva criada com sucesso para o usuário " + usuario.getNomeCompleto());
        return novaReserva;
    }
    }
    
    class IntervaloReservavel extends IntervaloDeUso
    {
        /*
         * O intervalo de uso mas com métodos que posibilite sua reseva.
         * 
         */
        private LocalDateTime inicio;
        private LocalDateTime fim;
        
        IntervaloReservavel(LocalDateTime inicio, LocalDateTime fim)
        {
           super(inicio, fim);
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Intervalo.formatoData);
           this.inicio = LocalDateTime.parse(super.inicio, formatter);
           this.fim = LocalDateTime.parse(super.fim, formatter);
        }

        IntervaloReservavel(IntervaloDeUso interval)
        {
            super.inicio = interval.inicio;
            super.fim = interval.fim;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Intervalo.formatoData);
            this.inicio = LocalDateTime.parse(super.inicio, formatter);
            this.fim = LocalDateTime.parse(super.fim, formatter);
        }
        
        List<Aparelho> agregarMaquinasPeloDia(int diaDoAno)
        {
            List<Aparelho> maquinasUteis = new ArrayList();
            if(fim.getDayOfYear() != inicio.getDayOfYear())
            {
                return null;
            }
            
            List<DiaDaReserva> dias =  R_Dia.loadAll();
            if (dias == null || dias.isEmpty())
                return null;
            
            for(DiaDaReserva d : dias)
            {
                if( d.getDiaDoAno() == diaDoAno && d.tempoDeFuncionamento > 0 )
                {
                    if( d.tempoDisponivel > 0 ) 
                    {
                        maquinasUteis.add(d.getAparelho());
                    }
                }
            }
            
            return maquinasUteis;
        }
       
       public Reserva fazerReserva(Usuario user, Aparelho aparelho, LocalDateTime inicio, LocalDateTime fim)
       {
           IntervaloReservavel intervalo = new IntervaloReservavel(inicio, fim);
           
           DiaReservavel dia = new DiaReservavel(inicio, aparelho);
           DiaDaReserva diaParaReserva = dia.terDiaParaReserva();
           
           IntervaloDeUso intervaloDaReserva = new IntervaloDeUso(aparelho, inicio, fim);
           R_Intervalo.create(intervaloDaReserva);
           
           Reserva novaReserva = new Reserva(user, intervaloDaReserva);
           R_Reserva.create(novaReserva);
           return novaReserva;
       }
       
       public LocalDateTime getInicioReservavel()
       {
           return this.inicio;
       }
       
       public LocalDateTime getFimReservavel()
       {
           return this.fim;
       }
    }
    
    /*
    Agenda(Database database)
    {
        R_Aparelho = new AparelhoRepository(database);
        R_Aparelho.create(teste);
        R_Usuario = new UsuarioRepository(database);
        R_Reserva = new ReservaRepository(database);
        R_Intervalo = new RepositoriosParaAgenda.IntervaloDeUsoRepository(database);
        R_Dia = new RepositoriosParaAgenda.DiaDaReservaRepository(database);
    }
    */
   
    /*
     
    
    Agenda()
    {
        for (int i = 0; i < tempoDeFuncionamentoSemana.length; i++)
        {
            tempoDeFuncionamentoSemana[i] = 8.0;
        }
    }
    
    Agenda(double tempo) throws Exception
    {
        if (tempo < 0.0 || tempo > 24.0) {
            throw new Exception("Tempo padrão inválido: " + tempo + "h. Deve estar entre 0.0 e 24.0 horas.");
        }
    
        tempoDeFuncionamentoSemana = new double[7];
        for (int i = 0; i < tempoDeFuncionamentoSemana.length; i++) {
            tempoDeFuncionamentoSemana[i] = tempo;
        }
    }
    
    Agenda(double dom, double seg, double ter, double qua, double qui, double sex, double sab) throws Exception
    {
        double[] novosTempos = {dom, seg, ter, qua, qui, sex, sab};
        StringBuilder erros = new StringBuilder();
        
        for (int i = 0; i < novosTempos.length; i++)
        {
            if (novosTempos[i] < 0.0 || novosTempos[i] > 24.0) {
            erros.append(" - ")
                 .append(diasSemana[i])
                 .append(": ")
                 .append(novosTempos[i])
                 .append(" horas (deve estar entre 0.0 e 24.0)\n");
        }
    }

    if (erros.length() > 0) {
        throw new Exception("Foram encontrados os seguintes erros nos tempos de funcionamento:\n" + erros.toString());
    }

    tempoDeFuncionamentoSemana = novosTempos.clone();
    }
    */
    
    public List<Aparelho> terMaquinas(DiaDaReserva dia, LocalDateTime inicio, LocalDateTime fim)
    {
        IntervaloReservavel interval = new IntervaloReservavel(inicio, fim);
        return interval.agregarMaquinasPeloDia(dia.getAno());
    }

    public List<Aparelho> terMaquinas(DiaDaReserva dia, IntervaloDeUso tempo)
    {
        IntervaloReservavel interval = new IntervaloReservavel(tempo);
        return interval.agregarMaquinasPeloDia(dia.getAno());
    }
    
    public double[] getTempoDeFuncionamentoSemana()
    {
        return tempoDeFuncionamentoSemana.clone();
    }
}
