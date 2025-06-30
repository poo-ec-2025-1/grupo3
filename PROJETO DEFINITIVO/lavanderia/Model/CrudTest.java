package lavanderia.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.sql.SQLException;

public class CrudTest {
    
    public static void testAllCrudOperations(String databaseName) {
        // Inicializa o banco de dados
        Database database = new Database(databaseName);
        
        // Inicializa os repositórios
        UsuarioRepository usuarioRepo = new UsuarioRepository(database);
        AparelhoRepository aparelhoRepo = new AparelhoRepository(database);
        RepositoriosParaAgenda.IntervaloDeUsoRepository intervaloRepo = 
            new RepositoriosParaAgenda.IntervaloDeUsoRepository(database);
        RepositoriosParaAgenda.DiaDaReservaRepository diaRepo = 
            new RepositoriosParaAgenda.DiaDaReservaRepository(database);
        ReservaRepository reservaRepo = new ReservaRepository(database);

        try {
            // 1. TESTE USUÁRIO REPOSITORY
            System.out.println("=== TESTE USUÁRIO REPOSITORY ===");
            testUsuarioRepository(usuarioRepo);
            
            // 2. TESTE APARELHO REPOSITORY
            System.out.println("\n=== TESTE APARELHO REPOSITORY ===");
            testAparelhoRepository(aparelhoRepo);
            
            // 3. TESTE INTERVALO DE USO REPOSITORY
            System.out.println("\n=== TESTE INTERVALO DE USO REPOSITORY ===");
            testIntervaloRepository(intervaloRepo, aparelhoRepo);
            
            // 4. TESTE DIA DA RESERVA REPOSITORY
            System.out.println("\n=== TESTE DIA DA RESERVA REPOSITORY ===");
            testDiaRepository(diaRepo, aparelhoRepo);
            
            // 5. TESTE RESERVA REPOSITORY
            System.out.println("\n=== TESTE RESERVA REPOSITORY ===");
            testReservaRepository(reservaRepo, usuarioRepo, intervaloRepo, diaRepo, aparelhoRepo);
            
        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                database.close();
            } catch (Exception e) {
                System.err.println("Erro ao fechar o banco de dados: " + e.getMessage());
            }
        }
    }
    
    private static void testUsuarioRepository(UsuarioRepository repo) {
        try {
            // CREATE
            Usuario usuario = new Usuario("João Silva", 12345, "senha123");
            usuario.depositar(100.0);
            Usuario usuarioCriado = repo.create(usuario);
            System.out.println("create(): " + usuarioCriado.mostrarDados());
            
            // LOAD FROM ID
            Usuario usuarioCarregado = repo.loadFromId(usuarioCriado.getId());
            if (usuarioCarregado != null) {
                System.out.println("loadFromId(): " + usuarioCarregado.mostrarDados());
                
                // UPDATE
                usuarioCarregado.setNomeCompleto("João Silva Atualizado");
                try {
                    repo.update(usuarioCarregado);
                    System.out.println("update(): Sucesso");
                } catch (SQLException e) {
                    System.out.println("update(): Erro - " + e.getMessage());
                }
            } else {
                System.out.println("loadFromId(): Usuário não encontrado");
            }
            
            // LOAD ALL
            List<Usuario> usuarios = repo.loadAll();
            System.out.println("loadAll(): " + (usuarios != null ? usuarios.size() : 0) + " usuários");
            
            // DELETE POR ID
            if (usuarioCarregado != null) {
                repo.deletePorId(usuarioCarregado.getId());
                System.out.println("deletePorId(): Executado");
            }
            
            // DELETE
            Usuario outroUsuario = new Usuario("Maria", 54321, "senha456");
            Usuario usuarioCriado2 = repo.create(outroUsuario);
            repo.delete(usuarioCriado2);
            System.out.println("delete(): Executado");
            
        } catch (Exception e) {
            System.err.println("Erro no teste de usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testAparelhoRepository(AparelhoRepository repo) {
        try {
            // CREATE
            Aparelho aparelho = new Aparelho("Lavadora X", "Lavadora de roupas", 10, true, 50.0);
            Aparelho aparelhoCriado = repo.create(aparelho);
            System.out.println("create(): " + aparelhoCriado.getModelo() + " ID: " + aparelhoCriado.getId());
            
            // LOAD FROM ID
            Aparelho aparelhoCarregado = repo.loadFromId(aparelhoCriado.getId());
            if (aparelhoCarregado != null) {
                System.out.println("loadFromId(): " + aparelhoCarregado.getModelo());
                
                // UPDATE
                aparelhoCarregado.setDescricao("Lavadora atualizada");
                repo.update(aparelhoCarregado);
                System.out.println("update(): Executado");
            } else {
                System.out.println("loadFromId(): Aparelho não encontrado");
            }
            
            // LOAD ALL
            List<Aparelho> aparelhos = repo.loadAll();
            System.out.println("loadAll(): " + (aparelhos != null ? aparelhos.size() : 0) + " aparelhos");
            
            // DELETE POR ID
            if (aparelhoCarregado != null) {
                repo.deletePorId(aparelhoCarregado.getId());
                System.out.println("deletePorId(): Executado");
            }
            
            // DELETE
            Aparelho outroAparelho = new Aparelho("Secadora Y", "Secadora", 15, true, 75.0);
            Aparelho aparelhoCriado2 = repo.create(outroAparelho);
            repo.delete(aparelhoCriado2);
            System.out.println("delete(): Executado");
            
        } catch (Exception e) {
            System.err.println("Erro no teste de aparelho: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testIntervaloRepository(RepositoriosParaAgenda.IntervaloDeUsoRepository repo, 
                                              AparelhoRepository aparelhoRepo) {
        Aparelho aparelhoCriado = null;
        IntervaloDeUso intervaloCarregado = null;
        
        try {
            // Cria aparelho para o teste
            Aparelho aparelho = new Aparelho("Aparelho Teste", "Para teste", 5, true, 30.0);
            aparelhoCriado = aparelhoRepo.create(aparelho);
            
            // CREATE
            LocalDateTime inicio = LocalDateTime.of(2025, 6, 30, 10, 0);
            LocalDateTime fim = LocalDateTime.of(2025, 6, 30, 11, 0);
            IntervaloDeUso intervalo = new IntervaloDeUso(aparelhoCriado, inicio, fim);
            repo.create(intervalo);
            System.out.println("create(): ID " + intervalo.getId());
            
            // LOAD FROM ID
            intervaloCarregado = repo.loadFromId(intervalo.getId());
            if (intervaloCarregado != null) {
                System.out.println("loadFromId(): Intervalo encontrado");
                
                // UPDATE
                intervaloCarregado.setStatus("EM_USO");
                boolean sucesso = repo.update(intervaloCarregado);
                System.out.println("update(): " + sucesso);
            } else {
                System.out.println("loadFromId(): Intervalo não encontrado");
            }
            
            // LOAD ALL
            List<IntervaloDeUso> intervalos = repo.loadAll();
            System.out.println("loadAll(): " + (intervalos != null ? intervalos.size() : 0) + " intervalos");
            
            // DELETE
            if (intervaloCarregado != null) {
                boolean sucesso = repo.delete(intervaloCarregado);
                System.out.println("delete(): " + sucesso);
            }
            
        } catch (Exception e) {
            System.err.println("Erro no teste de intervalo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Limpa aparelho teste
            try {
                if (aparelhoCriado != null) {
                    aparelhoRepo.delete(aparelhoCriado);
                }
            } catch (Exception e) {
                System.err.println("Erro ao limpar aparelho de teste: " + e.getMessage());
            }
        }
    }
    
    private static void testDiaRepository(RepositoriosParaAgenda.DiaDaReservaRepository repo, 
                                        AparelhoRepository aparelhoRepo) {
        Aparelho aparelhoCriado = null;
        DiaDaReserva diaCarregado = null;
        
        try {
            // Cria aparelho para o teste
            Aparelho aparelho = new Aparelho("Aparelho Dia", "Para teste dia", 8, true, 40.0);
            aparelhoCriado = aparelhoRepo.create(aparelho);
            
            // CREATE
            DiaDaReserva dia = new DiaDaReserva(aparelhoCriado, 2025, 1, 180, 24.0, 12.0);
            repo.create(dia);
            System.out.println("create(): ID " + dia.getIdDiaDaReserva());
            
            // LOAD FROM ID
            diaCarregado = repo.loadFromId(dia.getIdDiaDaReserva());
            if (diaCarregado != null) {
                System.out.println("loadFromId(): Ano " + diaCarregado.getAno());
                
                // UPDATE
                diaCarregado.indisponibilzarTempo(2.0);
                boolean sucesso = repo.update(diaCarregado);
                System.out.println("update(): " + sucesso);
            } else {
                System.out.println("loadFromId(): Dia não encontrado");
            }
            
            // LOAD ALL
            List<DiaDaReserva> dias = repo.loadAll();
            System.out.println("loadAll(): " + (dias != null ? dias.size() : 0) + " dias");
            
            // DELETE
            if (diaCarregado != null) {
                boolean sucesso = repo.delete(diaCarregado);
                System.out.println("delete(): " + sucesso);
            }
            
        } catch (Exception e) {
            System.err.println("Erro no teste de dia: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Limpa aparelho teste
            try {
                if (aparelhoCriado != null) {
                    aparelhoRepo.delete(aparelhoCriado);
                }
            } catch (Exception e) {
                System.err.println("Erro ao limpar aparelho de teste: " + e.getMessage());
            }
        }
    }
    
    private static void testReservaRepository(ReservaRepository repo, UsuarioRepository usuarioRepo,
                                            RepositoriosParaAgenda.IntervaloDeUsoRepository intervaloRepo, RepositoriosParaAgenda.DiaDaReservaRepository diaRepo,
                                            AparelhoRepository aparelhoRepo) {
        Usuario usuarioCriado = null;
        Aparelho aparelhoCriado = null;
        IntervaloDeUso intervalo = null;
        DiaDaReserva dia = null;
        
        try {
            // Cria dados para o teste
            Usuario usuario = new Usuario("Usuario Reserva", 11111, "senha");
            usuarioCriado = usuarioRepo.create(usuario);
            
            Aparelho aparelho = new Aparelho("Aparelho Reserva", "Para reserva", 6, true, 25.0);
            aparelhoCriado = aparelhoRepo.create(aparelho);
            
            LocalDateTime inicio = LocalDateTime.of(2025, 6, 30, 14, 0);
            LocalDateTime fim = LocalDateTime.of(2025, 6, 30, 15, 0);
            intervalo = new IntervaloDeUso(aparelhoCriado, inicio, fim);
            intervaloRepo.create(intervalo);
            
            
            // CREATE
            Reserva reserva = new Reserva(usuarioCriado, intervalo);
            repo.create(reserva);
            System.out.println("create(): ID " + reserva.getId());
            
            // LOAD FROM ID
            Reserva reservaCarregada = repo.loadFromId(reserva.getId());
            if (reservaCarregada != null) {
                usuario = reservaCarregada.getUser();
                if (usuario != null) {
                    System.out.println("loadFromId(): Usuario: " + usuario.getNomeCompleto());
                } else {
                    System.out.println("loadFromId(): Usuário não encontrado na reserva");
                }
                
                // UPDATE
                reservaCarregada.getIntervalo().setStatus("EM_USO");
                boolean sucesso = repo.update(reservaCarregada);
                System.out.println("update(): " + sucesso);
            } else {
                System.out.println("loadFromId(): Reserva não encontrada");
            }
            
            // LOAD ALL
            List<Reserva> reservas = repo.loadAll();
            System.out.println("loadAll(): " + (reservas != null ? reservas.size() : 0) + " reservas");
            
            // DELETE
            if (reservaCarregada != null) {
                boolean sucesso = repo.delete(reservaCarregada);
                System.out.println("delete(): " + sucesso);
            }
            
        } catch (Exception e) {
            System.err.println("Erro no teste de reserva: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Limpa dados de teste, mesmo em caso de erro
            try {
                if (intervalo != null) intervaloRepo.delete(intervalo);
                if (usuarioCriado != null) usuarioRepo.delete(usuarioCriado);
                if (aparelhoCriado != null) aparelhoRepo.delete(aparelhoCriado);
            } catch (Exception e) {
                System.err.println("Erro ao limpar dados de teste: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        testAllCrudOperations("test_database.db");
    }
}
