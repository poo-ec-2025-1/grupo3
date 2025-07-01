import java.time.*;
import java.util.List;

public class AgendaTest {
    
    private static Database db;
    private static Agenda agenda;
    private static UsuarioRepository R_Usuario;
    private static AparelhoRepository R_Aparelho;
    
    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTES DA AGENDA ===\n");
        
        // Inicializar componentes
        inicializarComponentes();
        
        try {
            // Executar todos os testes
            testDiaReservavel();
            testIntervaloReservavel();
            testMetodosPublicosAgenda();
            testCenarioCompleto();
            
            System.out.println("\n=== TODOS OS TESTES CONCLUÍDOS ===");
            
        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fechar banco de dados
            try {
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                System.err.println("Erro ao fechar banco: " + e.getMessage());
            }
        }
    }
    
    private static void inicializarComponentes() {
        System.out.println("--- Inicializando componentes ---");
        
        // Criar banco de dados de teste
        db = new Database("agenda_test.db");
        agenda = new Agenda(db);
        
        // Inicializar repositórios
        R_Usuario = new UsuarioRepository(db);
        R_Aparelho = new AparelhoRepository(db);
        
        System.out.println("[OK] Componentes inicializados\n");
    }
    
    private static void testDiaReservavel() {
        System.out.println("--- TESTANDO CLASSE DiaReservavel ---");
        
        try {
            // Criar dados de teste
            LocalDate dataReserva = LocalDate.of(2025, 7, 15);
            Aparelho aparelho1 = new Aparelho("Lavadora A", "Teste DiaReservavel", 8, true, 25.0);
            R_Aparelho.create(aparelho1);
            
            Usuario usuario1 = new Usuario("Maria Silva", 12345, "senha123");
            usuario1.depositar(100.0);
            R_Usuario.create(usuario1);
            
            // Teste 1: Criar DiaReservavel
            System.out.println("Teste 1 - Criação de DiaReservavel:");
            Agenda.DiaReservavel diaReservavel = new Agenda.DiaReservavel(dataReserva, aparelho1);
            System.out.println("[OK] DiaReservavel criado para " + dataReserva);
            
            // Teste 2: Verificar se já está reservado (deve retornar null inicialmente)
            System.out.println("Teste 2 - Verificação de reserva existente:");
            Integer jaReservado = diaReservavel.jaEstaReservado();
            if (jaReservado == null) {
                System.out.println("[OK] Dia não possui reservas anteriores");
            } else {
                System.out.println("[INFO] Dia já possui reserva com ID: " + jaReservado);
            }
            
            // Teste 3: Obter dia para reserva
            System.out.println("Teste 3 - Obter dia para reserva:");
            DiaDaReserva diaParaReserva = diaReservavel.terDiaParaReserva();
            if (diaParaReserva != null) {
                System.out.println("[OK] Dia para reserva obtido - ID: " + diaParaReserva.getId());
                System.out.println("     Tempo disponível: " + diaParaReserva.getTempoDisponivel() + "h");
            } else {
                System.out.println("[ERRO] Não foi possível obter dia para reserva");
            }
            
            // Teste 4: Criar reserva completa
            System.out.println("Teste 4 - Criar reserva completa:");
            IntervaloHorario horario = new IntervaloHorario(LocalTime.of(10, 0), LocalTime.of(12, 0));
            Reserva reserva = diaReservavel.criarReservaEAtualizarTempo(usuario1, aparelho1, dataReserva, horario);
            
            if (reserva != null) {
                System.out.println("[OK] Reserva criada com sucesso - ID: " + reserva.getId());
                System.out.println("     Usuário: " + reserva.getUser().getNomeCompleto());
                System.out.println("     Aparelho: " + reserva.getIntervalo().getAparelho().getModelo());
            } else {
                System.out.println("[ERRO] Falha ao criar reserva");
            }
            
            // Teste 5: Tentar criar reserva com dados inválidos
            System.out.println("Teste 5 - Teste com dados inválidos:");
            Reserva reservaInvalida = diaReservavel.criarReservaEAtualizarTempo(null, aparelho1, dataReserva, horario);
            if (reservaInvalida == null) {
                System.out.println("[OK] Reserva com dados inválidos rejeitada corretamente");
            } else {
                System.out.println("[ERRO] Reserva inválida foi aceita");
            }
            
        } catch (Exception e) {
            System.err.println("[ERRO] Falha no teste DiaReservavel: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    private static void testIntervaloReservavel() {
        System.out.println("--- TESTANDO CLASSE IntervaloReservavel ---");
        
        try {
            // Criar dados de teste
            LocalDateTime inicio = LocalDateTime.of(2025, 7, 16, 14, 0);
            LocalDateTime fim = LocalDateTime.of(2025, 7, 16, 16, 0);
            
            Aparelho aparelho2 = new Aparelho("Secadora B", "Teste IntervaloReservavel", 10, true, 35.0);
            R_Aparelho.create(aparelho2);
            
            Usuario usuario2 = new Usuario("João Santos", 54321, "senha456");
            usuario2.depositar(150.0);
            R_Usuario.create(usuario2);
            
            // Teste 1: Criar IntervaloReservavel
            System.out.println("Teste 1 - Criação de IntervaloReservavel:");
            Agenda.IntervaloReservavel intervaloReservavel = new Agenda.IntervaloReservavel(inicio, fim);
            System.out.println("[OK] IntervaloReservavel criado");
            System.out.println("     Início: " + intervaloReservavel.getInicioReservavel());
            System.out.println("     Fim: " + intervaloReservavel.getFimReservavel());
            
            // Teste 2: Criar um dia com tempo disponível primeiro
            System.out.println("Teste 2 - Preparar dia com tempo disponível:");
            Agenda.DiaReservavel diaPrep = new Agenda.DiaReservavel(inicio.toLocalDate(), aparelho2);
            DiaDaReserva diaPrepReserva = diaPrep.terDiaParaReserva();
            System.out.println("[OK] Dia preparado com " + diaPrepReserva.getTempoDisponivel() + "h disponíveis");
            
            // Teste 3: Buscar máquinas disponíveis
            System.out.println("Teste 3 - Buscar máquinas disponíveis:");
            List<Aparelho> maquinasDisponiveis = intervaloReservavel.agregarMaquinasPeloDia();
            if (maquinasDisponiveis != null && !maquinasDisponiveis.isEmpty()) {
                System.out.println("[OK] Encontradas " + maquinasDisponiveis.size() + " máquinas disponíveis:");
                for (Aparelho a : maquinasDisponiveis) {
                    System.out.println("     - " + a.getModelo());
                }
            } else {
                System.out.println("[INFO] Nenhuma máquina disponível encontrada");
            }
            
            // Teste 4: Fazer reserva através do IntervaloReservavel
            System.out.println("Teste 4 - Fazer reserva via IntervaloReservavel:");
            Reserva reservaIntervalo = intervaloReservavel.fazerReserva(usuario2, aparelho2);
            if (reservaIntervalo != null) {
                System.out.println("[OK] Reserva criada via IntervaloReservavel - ID: " + reservaIntervalo.getId());
            } else {
                System.out.println("[ERRO] Falha ao criar reserva via IntervaloReservavel");
            }
            
            // Teste 5: Testar com intervalo que cruza dias (deve falhar)
            System.out.println("Teste 5 - Teste com intervalo cruzando dias:");
            LocalDateTime inicioMultiDia = LocalDateTime.of(2025, 7, 17, 23, 0);
            LocalDateTime fimMultiDia = LocalDateTime.of(2025, 7, 18, 1, 0);
            Agenda.IntervaloReservavel intervaloMultiDia = new Agenda.IntervaloReservavel(inicioMultiDia, fimMultiDia);
            List<Aparelho> maquinasMultiDia = intervaloMultiDia.agregarMaquinasPeloDia();
            if (maquinasMultiDia == null) {
                System.out.println("[OK] Intervalo multi-dia rejeitado corretamente");
            } else {
                System.out.println("[AVISO] Intervalo multi-dia foi aceito");
            }
            
        } catch (Exception e) {
            System.err.println("[ERRO] Falha no teste IntervaloReservavel: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    private static void testMetodosPublicosAgenda() {
        System.out.println("--- TESTANDO MÉTODOS PÚBLICOS DA AGENDA ---");
        
        try {
            // Criar dados de teste
            Usuario usuario3 = new Usuario("Ana Costa", 98765, "senha789");
            usuario3.depositar(200.0);
            R_Usuario.create(usuario3);
            
            Aparelho aparelho3 = new Aparelho("Lavadora C", "Teste métodos públicos", 12, true, 45.0);
            R_Aparelho.create(aparelho3);
            
            LocalDateTime inicio = LocalDateTime.of(2025, 7, 18, 8, 0);
            LocalDateTime fim = LocalDateTime.of(2025, 7, 18, 10, 0);
            
            // Teste 1: fazerReserva(Usuario, Aparelho, LocalDateTime, LocalDateTime)
            System.out.println("Teste 1 - Método fazerReserva da Agenda:");
            Reserva reservaAgenda = agenda.fazerReserva(usuario3, aparelho3, inicio, fim);
            if (reservaAgenda != null) {
                System.out.println("[OK] Reserva criada via método da Agenda - ID: " + reservaAgenda.getId());
            } else {
                System.out.println("[ERRO] Falha ao criar reserva via método da Agenda");
            }
            
            // Preparar dia para próximos testes
            Agenda.DiaReservavel diaPrep2 = new Agenda.DiaReservavel(inicio.toLocalDate(), aparelho3);
            diaPrep2.terDiaParaReserva();
            
            // Teste 2: terMaquinas(LocalDateTime, LocalDateTime)
            System.out.println("Teste 2 - Método terMaquinas com LocalDateTime:");
            List<Aparelho> maquinas1 = agenda.terMaquinas(inicio, fim);
            if (maquinas1 != null) {
                System.out.println("[OK] Método terMaquinas executado - " + maquinas1.size() + " máquinas encontradas");
            } else {
                System.out.println("[INFO] Nenhuma máquina encontrada via terMaquinas");
            }
            
            // Teste 3: terMaquinas(IntervaloReservavel)
            System.out.println("Teste 3 - Método terMaquinas com IntervaloReservavel:");
            Agenda.IntervaloReservavel intervaloTeste = new Agenda.IntervaloReservavel(inicio, fim);
            List<Aparelho> maquinas2 = agenda.terMaquinas(intervaloTeste);
            if (maquinas2 != null) {
                System.out.println("[OK] Método terMaquinas com IntervaloReservavel executado - " + maquinas2.size() + " máquinas");
            } else {
                System.out.println("[INFO] Nenhuma máquina encontrada via terMaquinas com IntervaloReservavel");
            }
            
            // Teste 4: getTempoDeFuncionamentoSemana()
            System.out.println("Teste 4 - Método getTempoDeFuncionamentoSemana:");
            double[] temposSemana = agenda.getTempoDeFuncionamentoSemana();
            if (temposSemana != null && temposSemana.length == 7) {
                System.out.println("[OK] Tempos de funcionamento obtidos:");
                String[] dias = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
                for (int i = 0; i < temposSemana.length; i++) {
                    System.out.println("     " + dias[i] + ": " + temposSemana[i] + "h");
                }
            } else {
                System.out.println("[ERRO] Falha ao obter tempos de funcionamento");
            }
            
        } catch (Exception e) {
            System.err.println("[ERRO] Falha no teste de métodos públicos: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    private static void testCenarioCompleto() {
        System.out.println("--- TESTE DE CENÁRIO COMPLETO ---");
        
        try {
            // Cenário: Múltiplos usuários tentando reservar o mesmo aparelho
            Usuario usuario4 = new Usuario("Pedro Lima", 11111, "senha111");
            usuario4.depositar(100.0);
            R_Usuario.create(usuario4);
            
            Usuario usuario5 = new Usuario("Sofia Oliveira", 22222, "senha222");
            usuario5.depositar(100.0);
            R_Usuario.create(usuario5);
            
            Aparelho aparelhoPopular = new Aparelho("Lavadora Popular", "Muito requisitada", 15, true, 30.0);
            R_Aparelho.create(aparelhoPopular);
            
            LocalDate dataPopular = LocalDate.of(2025, 7, 20);
            IntervaloHorario horario1 = new IntervaloHorario(LocalTime.of(9, 0), LocalTime.of(11, 0));
            IntervaloHorario horario2 = new IntervaloHorario(LocalTime.of(11, 0), LocalTime.of(13, 0));
            IntervaloHorario horario3 = new IntervaloHorario(LocalTime.of(13, 0), LocalTime.of(15, 0));
            
            System.out.println("Cenário: Múltiplas reservas no mesmo aparelho e dia");
            
            // Primeira reserva
            Agenda.DiaReservavel diaPopular = new Agenda.DiaReservavel(dataPopular, aparelhoPopular);
            Reserva reserva1 = diaPopular.criarReservaEAtualizarTempo(usuario4, aparelhoPopular, dataPopular, horario1);
            if (reserva1 != null) {
                System.out.println("[OK] Primeira reserva criada (9h-11h)");
            }
            
            // Segunda reserva
            Reserva reserva2 = diaPopular.criarReservaEAtualizarTempo(usuario5, aparelhoPopular, dataPopular, horario2);
            if (reserva2 != null) {
                System.out.println("[OK] Segunda reserva criada (11h-13h)");
            }
            
            // Terceira reserva
            Reserva reserva3 = diaPopular.criarReservaEAtualizarTempo(usuario4, aparelhoPopular, dataPopular, horario3);
            if (reserva3 != null) {
                System.out.println("[OK] Terceira reserva criada (13h-15h)");
            }
            
            // Verificar tempo restante
            DiaDaReserva diaFinal = diaPopular.terDiaParaReserva();
            System.out.println("[INFO] Tempo restante no dia: " + diaFinal.getTempoDisponivel() + "h");
            
            // Tentar reserva que excede o tempo disponível
            IntervaloHorario horarioExcessivo = new IntervaloHorario(LocalTime.of(15, 0), LocalTime.of(20, 0)); // 5 horas
            Reserva reservaExcessiva = diaPopular.criarReservaEAtualizarTempo(usuario5, aparelhoPopular, dataPopular, horarioExcessivo);
            if (reservaExcessiva == null) {
                System.out.println("[OK] Reserva excessiva rejeitada corretamente");
            } else {
                System.out.println("[AVISO] Reserva excessiva foi aceita");
            }
            
            // Verificar disponibilidade final
            LocalDateTime consultaInicio = dataPopular.atTime(LocalTime.of(16, 0));
            LocalDateTime consultaFim = dataPopular.atTime(LocalTime.of(18, 0));
            List<Aparelho> maquinasFinais = agenda.terMaquinas(consultaInicio, consultaFim);
            
            System.out.println("[INFO] Consulta final de disponibilidade:");
            if (maquinasFinais != null && !maquinasFinais.isEmpty()) {
                System.out.println("     Máquinas ainda disponíveis: " + maquinasFinais.size());
                for (Aparelho a : maquinasFinais) {
                    System.out.println("     - " + a.getModelo());
                }
            } else {
                System.out.println("     Nenhuma máquina disponível no horário consultado");
            }
            
        } catch (Exception e) {
            System.err.println("[ERRO] Falha no cenário completo: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
}