import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o programa...");

        // Instanciar o Database
        Database database = new Database("lavanderia.db");
        System.out.println("Database instanciado.");

        // Criar repositórios e forçar criação das tabelas
        try {
            UsuarioRepository usuarioRepository = new UsuarioRepository(database);
            System.out.println("Repositório de usuários criado.");
            AparelhoRepository aparelhoRepository = new AparelhoRepository(database);
            System.out.println("Repositório de aparelhos criado.");
        } catch (Exception e) {
            System.err.println("Erro ao criar repositórios: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Criar usuários
        Usuario cliente = new Cliente("João Silva", 12345, "senha123");
        Usuario admin = new Administrador("Maria Oliveira", 67890, "admin456");
        UsuarioRepository usuarioRepository = new UsuarioRepository(database);
        usuarioRepository.create(cliente);
        usuarioRepository.create(admin);
        System.out.println("Usuários criados e salvos.");

        // Criar aparelhos
        Aparelho lavadora = new MaquinaDeLavar(1, "Lavadora Eco", "Lavadora de 8kg", true, 8);
        Aparelho secadora = new Secadora(2, "Secadora Pro", "Secadora de 6kg", true, 6);
        AparelhoRepository aparelhoRepository = new AparelhoRepository(database);
        aparelhoRepository.create(lavadora);
        aparelhoRepository.create(secadora);
        System.out.println("Aparelhos criados e salvos.");

        // Testar reservas com Agenda
        LocalDateTime agora = LocalDateTime.now();
        System.out.println("Tentando reservar lavadora...");
        lavadora.reservar();
        System.out.println("Tentando reservar secadora...");
        secadora.reservar();

        // Testar liberação
        System.out.println("Liberando lavadora...");
        lavadora.liberar();

        // Testar cobranças
        System.out.println("Testando cobrança para cliente com lavadora...");
        Caixa.cobrar(cliente, lavadora);

        System.out.println("Testando cobrança para administrador com secadora...");
        Caixa.cobrar(admin, secadora);

        // Carregar e exibir transações
        List<Caixa.Transaction> transacoes = Caixa.getTransacoesPorMatricula(12345);
        if (transacoes != null) {
            System.out.println("Transações para matrícula 12345: " + transacoes.size());
            for (Caixa.Transaction t : transacoes) {
                System.out.println("Matrícula: " + t.getMatricula() + ", Valor: R$ " + t.getValor() + 
                                   ", Data: " + t.getData() + ", Aparelho: " + t.getAparelho().getModelo());
            }
        } else {
            System.out.println("Nenhuma transação encontrada para matrícula 12345.");
        }

        // Carregar e exibir usuários
        List<Usuario> usuarios = usuarioRepository.loadAll();
        System.out.println("Usuários no banco: " + (usuarios != null ? usuarios.size() : 0));
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                System.out.println(u.mostrarDados());
            }
        }

        // Carregar e exibir aparelhos
        List<Aparelho> aparelhos = aparelhoRepository.loadAll();
        System.out.println("Aparelhos no banco: " + (aparelhos != null ? aparelhos.size() : 0));
        if (aparelhos != null) {
            for (Aparelho a : aparelhos) {
                System.out.println("Modelo: " + a.getModelo() + ", Descrição: " + a.getDescricao() + ", Reservado: " + a.estaReservado());
            }
        }

        // Fechar a conexão
        Caixa.closeConnection();
        System.out.println("Teste concluído. Verifique o banco de dados.");
    }
}